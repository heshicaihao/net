package com.heshicaihao.net.RxOK.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory() + "/DCIM/Camera";
    //public static String SDPATH=Environment.getExternalStorageDirectory().getPath()+"/"+cutnameString+".jpg";
    public static Uri cropImageUri;//保存裁剪图片的uri

    public static void saveBitmap(Bitmap bm, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 600) {
            baos.reset();
            options -= 10;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            //File f = new File(SDPATH, picName + ".JPEG");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBigBitmap(Bitmap bm, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 800) {
            baos.reset();
            options -= 10;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            //File f = new File(SDPATH, picName + ".JPEG");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir();
        }
        dir.delete();
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 获取文件的类型
     *
     * @param fileName ：文件名
     * @return 文件类型
     */
    public static String getFileType(String fileName) {
        // TODO Auto-generated method stub
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }

    /**
     * 获取sd卡路径
     *
     * @return
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            // 这里可以修改为你的路径
            sdDir = new File(Environment.getExternalStorageDirectory()
                    + "/DCIM/Camera");

        } else {

        }
        return sdDir;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 将图片的旋转角度置为0  ，此方法可以解决某些机型拍照后图像，出现了旋转情况
     *
     * @param path
     * @return void
     */
    public static void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 照片的命名规则为：Mouton_20130125_173729.jpg
     *
     * @return
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    public static File createImageFile() throws IOException {

        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        // String timeStamp = format.format(new Date());
        // String imageFileName = "sheqing_" + timeStamp + ".jpg";
        String imageFileName = System.currentTimeMillis() + ".JPEG";
        // String imageFileName = System.currentTimeMillis() + ".jpg";
        File image = new File(PictureUtil.getAlbumDir(), imageFileName);
        LogUtils.d("--->mCurrentPhotoPath", image.getAbsolutePath());
        return image;
    }


    /**
     * 照片的命名规则为：Mouton_20130125_173729.jpg
     *
     * @return
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    public static File createImageFile(int position) throws IOException {

        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        // String timeStamp = format.format(new Date());
        // String imageFileName = "sheqing_" + timeStamp + ".jpg";
        String imageFileName = position + "_" + System.currentTimeMillis() + ".JPEG";
        // String imageFileName = System.currentTimeMillis() + ".jpg";
        File image = new File(PictureUtil.getAlbumDir(), imageFileName);
        LogUtils.d("--->mCurrentPhotoPath", image.getAbsolutePath());
        return image;
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取选择图片的路径
     */
    public static String getRealPathFromURI(Uri contentUri, Context mContext) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String imagePath = "";
        Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
                // 部分手机获取不到路径，所以要重新设置路径
                if (imagePath == null || imagePath.equals("")) {
                    FileOutputStream fos = null;
                    try {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), contentUri);
                        String imageFileName = "Mouton_" + System.currentTimeMillis() + ".jpg";
                        // String imageFileName = System.currentTimeMillis() +
                        // ".jpg";
                        File image = new File(PictureUtil.getAlbumDir(), imageFileName);
                        fos = new FileOutputStream(image);
                        if (bm != null) {
                            bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
                        }
                        return image.getAbsolutePath();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            cursor.close();
        }
        return imagePath;
    }

    /**
     * 保存并压缩图片，并跳转到显示页面
     */
    public static File save(String path, Context mContext) {
        File photofile = null;
        if (path != null) {
            FileOutputStream fos = null;
            try {
                // File f = new File(path);
                Bitmap bm = PictureUtil.getSmallBitmap(path);
                int degree = FileUtils.getBitmapDegree(path);
                if (degree != 0) {// 旋转照片角度
                    bm = FileUtils.rotateBitmapByDegree(bm, degree);
                }
                // bm = rotateBitmap(bm, 90);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                while (baos.toByteArray().length / 1024 > 1000) {
                    baos.reset();
                    options -= 10;
                    if (options < 11) {//为了防止图片大小一直达不到200kb，options一直在递减，当options<0时，下面的方法会报错
                        // 也就是说即使达不到200kb，也就压缩到10了
                        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                        break;
                    }
                    bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }


                long fileName = System.currentTimeMillis();
                photofile = new File(PictureUtil.getAlbumDir(), fileName
                        + ".jpg");
                if (!photofile.exists()) {
                    try {
                        photofile.createNewFile();
                    } catch (Exception e1) {
                        try {
                            photofile = new File(FileUtils.SDPATH, fileName
                                    + ".jpg");
                        } catch (Exception f) {
                            photofile = new File(
                                    Environment.getExternalStorageDirectory(),
                                    fileName + ".jpg");
                        }
                    }
                }
                fos = new FileOutputStream(photofile);

                try {
                    // File f = new File(SDPATH, picName + ".JPEG");
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                LogUtils.d("MainActivity error", e.toString());
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(mContext, "请先点击拍照按钮拍摄照片或选择图片", Toast.LENGTH_SHORT)
                    .show();
        }
        return photofile;
    }

    /**
     * 保存并压缩图片，并跳转到显示页面
     */
    public static File save(String path, Context mContext, int position) {
        File photofile = null;
        if (path != null) {
            FileOutputStream fos = null;
            try {
                // File f = new File(path);

                Bitmap bm = PictureUtil.getSmallBitmap(path);

                int degree = FileUtils.getBitmapDegree(path);

                if (degree != 0) {// 旋转照片角度
                    bm = FileUtils.rotateBitmapByDegree(bm, degree);
                }
                // bm = rotateBitmap(bm, 90);
                String fileName = position + "_" + System.currentTimeMillis();
                photofile = new File(PictureUtil.getAlbumDir(), fileName
                        + ".JPEG");
                if (!photofile.exists()) {
                    try {
                        photofile.createNewFile();
                    } catch (Exception e1) {
                        try {
                            photofile = new File(FileUtils.SDPATH, fileName
                                    + ".JPEG");
                        } catch (Exception f) {
                            photofile = new File(
                                    Environment.getExternalStorageDirectory(),
                                    fileName + ".JPEG");
                        }
                    }
                }
                fos = new FileOutputStream(photofile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                while (baos.toByteArray().length / 1024 > 600) {
                    baos.reset();
                    options -= 10;
                    bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }
                try {
                    // File f = new File(SDPATH, picName + ".JPEG");
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                LogUtils.d("MainActivity error", e.toString());
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(mContext, "请先点击拍照按钮拍摄照片或选择图片", Toast.LENGTH_SHORT)
                    .show();
        }
        return photofile;
    }

    public static int dp2px(Context context, int dpValue) {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * 删除文件夹以及文件夹里面的东西
     *
     * @param file
     */
//	public static void delete(File file) {
//		if (file.isFile()) {
//			file.delete();
//			return;
//		}
//		if (file.isDirectory()) {
//			File[] childFiles = file.listFiles();
//			if (childFiles == null || childFiles.length == 0) {
//				file.delete();
//				return;
//			}
//
//			for (int i = 0; i < childFiles.length; i++) {
//				delete(childFiles[i]);
//			}
//			file.delete();
//		}
//	}
    public static void delete(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件.
     *
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }
}
