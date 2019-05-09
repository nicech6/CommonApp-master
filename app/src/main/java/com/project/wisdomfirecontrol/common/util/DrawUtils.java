package com.project.wisdomfirecontrol.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 绘制工具类
 * 功能：相对屏幕百分比转化为实际像素值，罩子层的实际显示区域， 不能分辨率下的等比转换
 * @author jiangxuwen
 * 
 */
public class DrawUtils {
	public static float sDensity = 1.0f;
	public static int sDensityDpi;
	public static int sWidthPixels;
	public static int sHeightPixels;
	
//	public static int sGridCellWidth;
//	public static int sGridCellHeight;
	/**
	 * 天气等需要大点的高度
	 */
//	public static int sGridLargeCellHeight;
	
	public static float sFontDensity;
	public static int sTouchSlop = 15; // 点击的最大识别距离，超过即认为是移动

	public static int sStatusHeight; // 平板中底边的状态栏高度
	private static Class sClass = null;
	private static Method sMethodForWidth = null;
	private static Method sMethodForHeight = null;

	private static int sScreenViewWidth = -1; // 罩子层可见区域的宽度
	private static int sScreenViewHeight = -1; // 罩子层可见区域的高度

	private static int sMiddleViewWidth = -1; // 罩子层可见区域的宽度
	private static int sMiddleViewHeight = -1; // 罩子层可见区域的高度

	public static int sMiddleViewOffsetX;

	private static float sScaleX = 1.0f; // 非默认屏幕宽度分辨率与默认分辨率的比例（默认480）
	private static float sScaleY = 1.0f; // 非默认屏幕高度度分辨率与默认分辨率的比例（默认800）

	public static int sNavBarLocation;
	private static int sRealWidthPixels;
	private static int sRealHeightPixels;
	private static int sNavBarWidth; // 虚拟键宽度
	private static int sNavBarHeight; // 虚拟键高度
	public static final int NAVBAR_LOCATION_RIGHT = 1;
	public static final int NAVBAR_LOCATION_BOTTOM = 2;


	/**
	 * dip/dp转像素
	 * 
	 * @param dipVlue
	 *            dip或 dp大小
	 * @return 像素值
	 */
	public static int dip2px(float dipVlue) {
		return (int) (dipVlue * sDensity + 0.5f);
	}

	/**
	 * 像素转dip/dp
	 * 
	 * @param pxValue
	 *            像素大小
	 * @return dip值
	 */
	public static int px2dip(float pxValue) {
		final float scale = sDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * sp 转 px
	 * 
	 * @param spValue
	 *            sp大小
	 * @return 像素值
	 */
	public static int sp2px(float spValue) {
		final float scale = sDensity;
		return (int) (scale * spValue);
	}

	/**
	 * 设置罩子层的
	 * @param width
	 * @param height
	 */
	public static void setScreenViewSize(int width, int height) {
		sScreenViewWidth = width;
		sScreenViewHeight = height;
	}

	/**
	 * <br>功能简述:设置中间层的高与宽
	 * @param width
	 * @param height
	 */
	public static void setMiddleViewSize(int width, int height) {
		sMiddleViewHeight = height;
		sMiddleViewWidth = width;
	}

	public static int getMiddleViewWidth() {
		return sMiddleViewWidth;
	}

	public static int getMiddleViewHeight() {
		return sMiddleViewHeight;
	}

	/**
	 * px转sp
	 * 
	 * @param pxValue
	 *            像素大小
	 * @return sp值
	 */
	public static int px2sp(float pxValue) {
		final float scale = sDensity;
		return (int) (pxValue / scale);
	}

	/**
	 * 根据指定的density转化为另一个指定的density的数值
	 * @param pxValue
	 * @param srcDensity
	 * @param dstDensity
	 * @return
	 */
	public static int switcherPx(float pxValue, float srcDensity, float dstDensity) {
		final float scale = dstDensity / srcDensity;
		return (int) (pxValue * scale);
	}
	/**
	 * 占屏幕的横向百分比转化为实际像素值
	 * @param percent
	 * @return
	 */
	public static int percentX2px(float percent) {
		return (int) (sScreenViewWidth * percent);
	}

	/**
	 * 占屏幕的竖向百分比转化为实际像素值
	 * @param percent
	 * @return
	 */
	public static int percentY2px(float percent) {
		return (int) (sScreenViewHeight * percent);
	}

	/**
	 * 以X轴为基准，像素值转为相对于当前设备的像素值
	 * @param srcValue
	 * @return
	 */
	public static int switchByX(int srcValue) {
		if (sScaleX != 0 && sScaleX != 1) {
			return (int) (srcValue * sScaleX);
		}
		return srcValue;
	}

	/**
	 * 以Y轴为基准，像素值转为相对于当前设备的像素值
	 * @param srcValue
	 * @return
	 */
	public static int switchByY(int srcValue) {
		if (sScaleY != 0 && sScaleY != 1) {
			return (int) (srcValue * sScaleY);
		}
		return srcValue;
	}

	private static boolean sReseted;
	
	public static void resetIfCache(Context context) {
		if (sReseted) {
			return;
		}
		resetForce(context);
	}
	
	/**
	 * <br>注意:强制reset
	 * @param context
	 */
	public static void resetForce(Context context) {
		if (context != null && null != context.getResources()) {
			sReseted = true;
			Resources res = context.getResources();
			DisplayMetrics metrics = res.getDisplayMetrics();
			sDensity = metrics.density;
			sFontDensity = metrics.scaledDensity;
			sWidthPixels = metrics.widthPixels;
			sHeightPixels = metrics.heightPixels;
			sDensityDpi = metrics.densityDpi;
			final boolean isLand = sWidthPixels > sHeightPixels;
			final int defaultWidth = isLand ? 800 : 480;
			final int defaultHeight = isLand ? 480 : 800;
			sScaleX = sWidthPixels / defaultWidth;
			sScaleY = sHeightPixels / defaultHeight;
			try {
				final ViewConfiguration configuration = ViewConfiguration.get(context);
				if (null != configuration) {
					sTouchSlop = configuration.getScaledTouchSlop();
				}
			} catch (Error e) {
			}
			resetNavBarHeight(context);
		}
	}

	private static void resetNavBarHeight(Context context) {
		if (context != null) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			try {
				if (sClass == null) {
					sClass = Class.forName("android.view.Display");
				}
				Point realSize = new Point();
				Method method = sClass.getMethod("getRealSize", Point.class);
				method.invoke(display, realSize);
				sRealWidthPixels = realSize.x;
				sRealHeightPixels = realSize.y;
				sNavBarWidth = realSize.x - sWidthPixels;
				sNavBarHeight = realSize.y - sHeightPixels;
			} catch (Exception e) {
				sRealWidthPixels = sWidthPixels;
				sRealHeightPixels = sHeightPixels;
				sNavBarHeight = 0;
			}
		}
		sNavBarLocation = getNavBarLocation();
	}

	public static int getNavBarLocation() {
		if (sRealWidthPixels > sWidthPixels) {
			return NAVBAR_LOCATION_RIGHT;
		}
		return NAVBAR_LOCATION_BOTTOM;
	}

	public static int getScreenWidth() {
		return sWidthPixels;
	}
	
	public static int getScreenHeight() {
		return sHeightPixels;
	}

	public static int getScreenViewWidth() {
		return sScreenViewWidth;
	}

	public static int getScreenViewHeight() {
		return sScreenViewHeight;
	}

	public static boolean isPort() {
		return sScreenViewWidth < sScreenViewHeight;
	}

	public static int getsRealWidthPixels() {
		return sRealWidthPixels;
	}

	public static int getsRealHeightPixels() {
		return sRealHeightPixels;
	}

	public static int getsNavBarWidth() {
		return sNavBarWidth;
	}

	public static int getsNavBarHeight() {
		return sNavBarHeight;
	}

	/**
	 * 减速的三次曲线插值
	 * 
	 * @param begin
	 * @param end
	 * @param t
	 * 应该位于[0, 1]
	 * @return
	 */
	public static float easeOut(float begin, float end, float t) {
		t = 1 - t;
		return begin + (end - begin) * (1 - t * t * t);
	}

	/**
	 * 返回根据性状进行勾勒的图片
	 * 
	 * @param context
	 * @param drawable 原图
	 * @param maskDrawable 勾勒的性状
	 * @return 勾勒完成的图
	 */
	public static BitmapDrawable getMaskIcon(Context context, Drawable drawable, Drawable maskDrawable) {
		try {
			Bitmap oldbmp = null;
			if (drawable != null) {
				// drawable 转换成 bitmap
				if (drawable instanceof BitmapDrawable) {
					// 如果传入的drawable是BitmapDrawable,就不必要生成新的bitmap
					oldbmp = ((BitmapDrawable) drawable).getBitmap();
				} else {
					oldbmp = createBitmapFromDrawable(drawable);
				}

			}
			Bitmap maskbmp = null;
			if (maskDrawable != null) {
				// drawable 转换成 bitmap
				if (maskDrawable instanceof BitmapDrawable) {
					// 如果传入的drawable是BitmapDrawable,就不必要生成新的bitmap
					maskbmp = ((BitmapDrawable) maskDrawable).getBitmap();
				} else {
					maskbmp = createBitmapFromDrawable(maskDrawable);
				}
			}

			return getMaskIcon(context, oldbmp, maskbmp);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static BitmapDrawable getMaskIcon(Context context, Bitmap bitmap, Drawable maskDrawable) {
		try {
			Bitmap maskbmp = null;
			if (maskDrawable != null) {
				// drawable 转换成 bitmap
				if (maskDrawable instanceof BitmapDrawable) {
					// 如果传入的drawable是BitmapDrawable,就不必要生成新的bitmap
					maskbmp = ((BitmapDrawable) maskDrawable).getBitmap();
				} else {
					maskbmp = createBitmapFromDrawable(maskDrawable);
				}
			}

			return getMaskIcon(context, bitmap, maskbmp);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 返回根据性状进行勾勒的图片
	 * @param context
	 * @param bitmap
	 * @param maskBitmap
	 * @return
	 */
	public static BitmapDrawable getMaskIcon(Context context, Bitmap bitmap, Bitmap maskBitmap) {
		try {
			if (maskBitmap == null) {
				return null;
			}
			int maskW = maskBitmap.getWidth();
			int maskH = maskBitmap.getHeight();
			Bitmap temp = Bitmap.createBitmap(maskW, maskH, Config.ARGB_8888);
			Canvas canvasTemp = new Canvas(temp);

			if (bitmap != null) {

				int width = bitmap.getWidth();
				int height = bitmap.getHeight();

				if (width == 0 || height == 0) {
					return null;
				}
				Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
				float scale =  1.0f;
				int offsetCutX = 0;
				int offsetCutY = 0;
				if (width > height) {
					offsetCutX = (width - height) / 2;
					scale = (float) maskH / height;
				} else {
					offsetCutY = (height - width) / 2;
					scale = (float) maskW / width;
				}
				matrix.postScale(scale, scale); // 设置缩放比例
				Bitmap newbmp = Bitmap.createBitmap(bitmap, offsetCutX, offsetCutY,
						width - 2 * offsetCutX, height - 2 * offsetCutY, matrix, true); // 建立新的
																							// bitmap
																							// ，其内容是对原
																							// bitmap的缩放后的图
				final int drawTop = Math.max(0, maskH - newbmp.getHeight());
				final int drawLeft = Math.max(0, (maskW - newbmp.getWidth()) / 2);
				if (newbmp != null && !newbmp.isRecycled()) {
					canvasTemp.drawBitmap(newbmp, drawLeft, drawTop, null);
				}
				matrix = null;
			}

			Paint paint = new Paint();
			PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
			paint.setXfermode(xfermode);
			if (!maskBitmap.isRecycled()) {
				canvasTemp.drawBitmap(maskBitmap, 0, 0, paint);
			}
			return new BitmapDrawable(context.getResources(), temp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static Bitmap createBitmapFromDrawable(final Drawable drawable) {

		if (drawable == null) {
			return null;
		}
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = null;
		final int intrinsicWidth = drawable.getIntrinsicWidth();
		final int intrinsicHeight = drawable.getIntrinsicHeight();

		try {
			Config config = drawable.getOpacity() != PixelFormat.OPAQUE
					? Config.ARGB_8888
					: Config.RGB_565;
			bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
		} catch (OutOfMemoryError e) {
			return null;
		}

		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
		drawable.draw(canvas);
		canvas = null;
		return bitmap;
	}

	/**
	 * 矩形将图片圆化
	 *
	 * @param bitmap 传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

		return output;
	}

	/**
	 * bitmap转为base64
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}
