package com.lxm.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by lxm on 17/2/18.
 *
 * 用于完成图片压缩
 */

public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight) {

        //将BitmapFactory.Options的inJustDecodeBounds参数设为true
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //从BitmapFactory.Options取出图片原始的宽高信息，他们对应与outWidth和outHeight等参数
        BitmapFactory.decodeResource(res,resId,options);
        //根据采样率的规则并结合目标view的所需大小计算出采样率isSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //将options.inJustDecodeBounds的参数设为false，然后重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        //根据采样率的规则并结合目标view的所需大小计算出采样率isSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //将options.inJustDecodeBounds的参数设为false，然后重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        final int width = options.outWidth;
        final int height = options.outHeight;

        int isSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while ((halfHeight/isSampleSize)>=reqHeight && (halfWidth/isSampleSize)>=reqWidth){
                isSampleSize *= 2;
            }
        }
        return isSampleSize;
    }
}
