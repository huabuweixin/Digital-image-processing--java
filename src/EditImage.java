import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class EditImage {
	//图像加运算的实现
       public static BufferedImage image_add(BufferedImage srcImage, BufferedImage image1,float x,float y) {  
		    int width = srcImage.getWidth(); // 获取原始图像的宽度  
		    int height = srcImage.getHeight(); // 获取原始图像的高度  
		    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width); // 从原始图像获取RGB数组  
		  
		    // 创建二维数组用于存储原始图像的RGB分量  
		    int rgb[] = new int[3];  
		    int rs[][] = new int[width][height]; // 存储R分量  
		    int gs[][] = new int[width][height]; // 存储G分量  
		    int bs[][] = new int[width][height]; // 存储B分量  
		  
		    // 遍历原始图像的每个像素，并分解其RGB值  
		    for (int j = 0; j < height; j++) {  
		        for (int i = 0; i < width; i++) {  
		            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值  
		            rs[i][j] = rgb[0]; // 存储R值  
		            gs[i][j] = rgb[1]; // 存储G值  
		            bs[i][j] = rgb[2]; // 存储B值  
		        }  
		    }  
		  
		    // 获取要叠加的图像的尺寸  
		    int width1 = image1.getWidth();  
		    int height1 = image1.getHeight();  
		    // 获取要叠加图像的RGB数组  
		    srcRGBs = image1.getRGB(0, 0, width1, height1, null, 0, width1);  
		  
		    // 确定新图像的尺寸，取两个图像中较小的尺寸  
		    float ftemp;  
		    int w = width;  
		    int h = height;  
		    if (width > width1) {  
		        w = width1; // 如果原始图像宽度大于要叠加的图像，则使用要叠加图像的宽度  
		    }  
		    if (height > height1) {  
		        h = height1; // 如果原始图像高度大于要叠加的图像，则使用要叠加图像的高度  
		    }  
		    // 创建新的BufferedImage对象，用于存储叠加后的图像  
		    BufferedImage destImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
		  
		    // 遍历新图像的每个像素，并计算叠加后的RGB值  
		    for (int j = 0; j < h; j++) {  
		        for (int i = 0; i < w; i++) {  
		            try {  
		                ImageUtil.decodeColor(srcRGBs[j * width1 + i], rgb); // 分解要叠加图像的RGB值  
		            } catch (Exception e) {  
		                // 处理异常，例如当索引超出数组范围时  
		                e.printStackTrace();  
		                System.out.println(i);  
		            } 
		            ftemp = rs[i][j] * x + rgb[0] * y; // R值  
		            rgb[0] = (int) (ftemp + 0.5f); // 取整，转换为整数  
		            ftemp = gs[i][j] * x + rgb[1] * y; // G值  
		            rgb[1] = (int) (ftemp + 0.5f); // 取整，转换为整数  
		            ftemp = bs[i][j] * x + rgb[2] * y; // B值  
		            rgb[2] = (int) (ftemp + 0.5f); // 取整，转换为整数  
		            // 将计算后的RGB值设置到新图像的对应像素上  
		            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));  
		        }  
		    }  
		  
		    // 返回叠加后的新图像  
		    return destImage;  
		}
	public static BufferedImage ImageNegative(BufferedImage image1) {
		// 获取图像的宽度和高度  
	    int width = image1.getWidth();  
	    int height = image1.getHeight(); 
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];  
	    int rs[][] = new int[width][height]; // 存储R分量  
	    int gs[][] = new int[width][height]; // 存储G分量  
	    int bs[][] = new int[width][height]; // 存储B分量 
	    for (int j = 0; j < height; j++) {  
	        for (int i = 0; i < width; i++) {  
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值  
	            rs[i][j] = rgb[0]; // 存储R值  
	            gs[i][j] = rgb[1]; // 存储G值  
	            bs[i][j] = rgb[2]; // 存储B值  
	        }  
	    }  
	    for(int j=0;j<height;j++) {
	    	for(int i=0;i<width;i++) {
	    		rs[i][j]=255-rs[i][j];
	    		gs[i][j]=255-gs[i][j];
	    		bs[i][j]=255-bs[i][j];
		        rgb[0] = (int) (rs[i][j] + 0.5f); // 取整，转换为整数  
		        rgb[1] = (int) (gs[i][j] + 0.5f); // 取整，转换为整数  
		        rgb[2] = (int)( bs[i][j] + 0.5f); // 取整，转换为整数  
	            image1.setRGB(i, j, ImageUtil.encodeColor(rgb)); 
	    	}
	    }
        return image1;
	}
 public static BufferedImage Imagewater(BufferedImage srcImage, BufferedImage image1, float f, int x, int y) {
		    if (srcImage == null || image1 == null) {
		        throw new IllegalArgumentException("srcImage and image1 cannot be null");
		    }
		    if (f < 0.0f || f > 1.0f) {
		        throw new IllegalArgumentException("f must be between 0.0 and 1.0");
		    }
		    if (x < 0 || x > srcImage.getWidth() - image1.getWidth()) {
		        throw new IllegalArgumentException("x is out of bounds");
		    }
		    if (y < 0 || y > srcImage.getHeight() - image1.getHeight()) {
		        throw new IllegalArgumentException("y is out of bounds");
		    }
		    int width = srcImage.getWidth(); // 获取原始图像的宽度  
		    int height = srcImage.getHeight(); // 获取原始图像的高度  
		    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width); // 从原始图像获取RGB数组  
		  
		    // 创建二维数组用于存储原始图像的RGB分量  
		    int rgb[] = new int[3];  
		    int rs[][] = new int[width][height]; // 存储R分量  
		    int gs[][] = new int[width][height]; // 存储G分量  
		    int bs[][] = new int[width][height]; // 存储B分量  
		  
		    // 遍历原始图像的每个像素，并分解其RGB值  
		    for (int j = 0; j < height; j++) {  
		        for (int i = 0; i < width; i++) {  
		            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值  
		            rs[i][j] = rgb[0]; // 存储R值  
		            gs[i][j] = rgb[1]; // 存储G值  
		            bs[i][j] = rgb[2]; // 存储B值  
		        }  
		    }  
		  
		    // 获取要叠加的图像的尺寸  
		    int width1 = image1.getWidth();  
		    int height1 = image1.getHeight();  
		    // 获取要叠加图像的RGB数组  
		    srcRGBs = image1.getRGB(0, 0, width1, height1, null, 0, width1);   
		    float ftemp;  
		    int w = width;  
		    int h = height;  
		    if (width > width1) {  
		        w = width;
		    }  
		    if (height > height1) {  
		        h = height; 
		    }  
		    BufferedImage destImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
		    for (int j = 0; j < h; j++) {  
		        for (int i = 0; i < w; i++) {  
		        	if (i >= x && i <= x + width1 && j >= y && j < y + height1) {
		                try {
		                    int color = srcRGBs[(j - y) * width1 + (i - x)];
		                    if (color != 0xFFFFFF) { // 检查像素是否为白色
		                        ImageUtil.decodeColor(color, rgb);
		                        if ((255-rgb[0])<30&&(255-rgb[1])<30&&(255-rgb[2])<30) {
		                            rgb[0] = rs[i][j];
		                            rgb[1] = gs[i][j];
		                            rgb[2] = bs[i][j];
		                        }
		                        ftemp = rs[i][j] * (1 - f) + rgb[0] * f; // R值
		                        rgb[0] = (int) (ftemp + 0.5f); // 取整，转换为整数
		                        ftemp = gs[i][j] * (1 - f) + rgb[1] * f; // G值
		                        rgb[1] = (int) (ftemp + 0.5f); // 取整，转换为整数
		                        ftemp = bs[i][j] * (1 - f) + rgb[2] * f; // B值
		                        rgb[2] = (int) (ftemp + 0.5f); // 取整，转换为整数
		                        // 将计算后的RGB值设置到新图像的对应像素上
		                        destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		                   } else {
		                	   ftemp = rs[i][j]; // R值
				                rgb[0] = (int) (ftemp + 0.5f);
				                ftemp = gs[i][j]; // G值
				                rgb[1] = (int) (ftemp + 0.5f);
				                ftemp = bs[i][j]; // B值
				                rgb[2] = (int) (ftemp + 0.5f);
		                        destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		                    }
		                } catch (Exception e) {
		                    e.printStackTrace();
		                    System.out.println(i);
		                }
		            } else {
		                ftemp = rs[i][j]; // R值
		                rgb[0] = (int) (ftemp + 0.5f);
		                ftemp = gs[i][j]; // G值
		                rgb[1] = (int) (ftemp + 0.5f);
		                ftemp = bs[i][j]; // B值
		                rgb[2] = (int) (ftemp + 0.5f);
		                destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		            }
		     	}  
		    }
		    return destImage;

 }
 public static BufferedImage ImagezoomN(BufferedImage srcImage,float x,float y) {//最近邻内插法
	 int width=srcImage.getWidth();
	 int height=srcImage.getHeight();
	 int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	 int rgb[] = new int[3];  
	 int rs[][] = new int[width][height];  
	 int gs[][] = new int[width][height]; 
	 int bs[][] = new int[width][height];
	 for (int j = 0; j < height; j++) {  
	        for (int i = 0; i < width; i++) {  
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0];  
	            gs[i][j] = rgb[1];  
	            bs[i][j] = rgb[2]; 
	        }  
	    }  
	 int width1=(int) (width*x+0.5f);
	 int height1=(int)(height*y+0.5f);
	 int rs1[][] = new int[width1][height1];
	 int gs1[][] = new int[width1][height1];  
	 int bs1[][] = new int[width1][height1]; 
	 float ftemp;
	 BufferedImage destImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
	 for(int j=0;j<height1;j++) {
		 for(int i=0;i<width1;i++) {
			rs1[i][j]=rs[(int)(i*(1/x)+0.5f)][(int)(j*(1/y)+0.5f)];
			gs1[i][j]=gs[(int)(i*(1/x)+0.5f)][(int)(j*(1/y)+0.5f)];
			bs1[i][j]=bs[(int)(i*(1/x)+0.5f)][(int)(j*(1/y)+0.5f)];
			ftemp = rs1[i][j]; 
            rgb[0] = (int) (ftemp + 0.5f);
            ftemp = gs1[i][j];
            rgb[1] = (int) (ftemp + 0.5f);
            ftemp = bs1[i][j];
            rgb[2] = (int) (ftemp + 0.5f);
            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		 }
	 }
	 return destImage;
	 
 }
 public static BufferedImage ImagezoomS(BufferedImage srcImage, float x, float y) { //双线性内插法
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int width1 = (int)(width * x);
	    int height1 = (int)(height * y);
	    int rs1[][] = new int[width1][height1]; 
	    int gs1[][] = new int[width1][height1]; 
	    int bs1[][] = new int[width1][height1]; 
	    float ftemp;
	    BufferedImage destImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height1; j++) {
	        for (int i = 0; i < width1; i++) {
	            float u = i * (1 / x);//计算在源图中的横坐标
	            float v = j * (1 / y);//计算在原图中的纵坐标
	            int u1 = (int) u;//(保留整数位)取整
	            int v1 = (int) v;
	            int u2 = Math.min(u1 + 1, width - 1);//计算下一个坐标，同时防止数组越界
	            int v2 = Math.min(v1 + 1, height - 1);
	            float fu = u - u1;//计算源图中的小数部分
	            float fv = v - v1;
	            rs1[i][j] = (int) ((1 - fu) * (1 - fv) * rs[u1][v1] + fu * (1 - fv) * rs[u2][v1] + (1 - fu) * fv * rs[u1][v2] + fu * fv * rs[u2][v2]);
	            gs1[i][j] = (int) ((1 - fu) * (1 - fv) * gs[u1][v1] + fu * (1 - fv) * gs[u2][v1] + (1 - fu) * fv * gs[u1][v2] + fu * fv * gs[u2][v2]);
	            bs1[i][j] = (int) ((1 - fu) * (1 - fv) * bs[u1][v1] + fu * (1 - fv) * bs[u2][v1] + (1 - fu) * fv * bs[u1][v2] + fu * fv * bs[u2][v2]);
	            ftemp = rs1[i][j]; 
	            rgb[0] = (int) (ftemp + 0.5f);
	            ftemp = gs1[i][j];
	            rgb[1] = (int) (ftemp + 0.5f);
	            ftemp = bs1[i][j];
	            rgb[2] = (int) (ftemp + 0.5f);
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    return destImage;
	}
 public static BufferedImage rotateImageN(BufferedImage srcImage, float angle) {//最近邻内插法旋转图片
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int destWidth = (int) (width * Math.abs(Math.cos(Math.toRadians(angle))) + height * Math.abs(Math.sin(Math.toRadians(angle))));
	    int destHeight = (int) (width * Math.abs(Math.sin(Math.toRadians(angle))) + height * Math.abs(Math.cos(Math.toRadians(angle))));
	    BufferedImage destImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
	    //旋转中心
	    float srcCenterX = width / 2f;
	    float srcCenterY = height / 2f;
	    float destCenterX = destWidth / 2f;
	    float destCenterY = destHeight / 2f;
	    // 变换矩阵组件
	    double cosTheta = Math.cos(Math.toRadians(angle));
	    double sinTheta = Math.sin(Math.toRadians(angle));
	    for (int y = 0; y < destHeight; y++) {
	        for (int x = 0; x < destWidth; x++) {
	            // 将坐标平移到中心
	            int srcX = (int) ((x - destCenterX) * cosTheta + (y - destCenterY) * sinTheta + srcCenterX);
	            int srcY = (int) (-(x - destCenterX) * sinTheta + (y - destCenterY) * cosTheta + srcCenterY);

	            // 检查转换后的坐标是否在范围内
	            if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
	                // 获取最近邻的RGB值
	                rgb[0]=rs[srcX][srcY]; 
	                rgb[1]=gs[srcX][srcY]; 
	                rgb[2]=bs[srcX][srcY];	                
	                destImage.setRGB(x, y, ImageUtil.encodeColor(rgb));
	            }else {
	            rgb[0]=255; 
                rgb[1]=255; 
                rgb[2]=255; 
                destImage.setRGB(x, y, ImageUtil.encodeColor(rgb));
	            }
	        }
	    }
	    return destImage;
	}

 public static BufferedImage rotateImageS(BufferedImage srcImage, float angle) {
     int width = srcImage.getWidth();
     int height = srcImage.getHeight();
     int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
     int rgb[] = new int[3];
     int rs[][] = new int[width][height];
     int gs[][] = new int[width][height];
     int bs[][] = new int[width][height];
     for (int j = 0; j < height; j++) {
         for (int i = 0; i < width; i++) {
             ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
             rs[i][j] = rgb[0]; 
             gs[i][j] = rgb[1]; 
             bs[i][j] = rgb[2]; 
         }
     }

     double sinTheta = Math.sin(Math.toRadians(angle));
     double cosTheta = Math.cos(Math.toRadians(angle));

     // 计算旋转后图像的宽度和高度
     int destWidth = (int) (width * Math.abs(cosTheta) + height * Math.abs(sinTheta));
     int destHeight = (int) (width * Math.abs(sinTheta) + height * Math.abs(cosTheta));

     BufferedImage destImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
     // 旋转中心
     float srcCenterX = width / 2f;
     float srcCenterY = height / 2f;
     float destCenterX = destWidth / 2f;
     float destCenterY = destHeight / 2f;

     for (int y = 0; y < destHeight; y++) {
         for (int x = 0; x < destWidth; x++) {
             // 将坐标平移到中心
             int srcX = (int) ((x - destCenterX) * cosTheta + (y - destCenterY) * sinTheta + srcCenterX);
             int srcY = (int) (-(x - destCenterX) * sinTheta + (y - destCenterY) * cosTheta + srcCenterY);

             // 检查转换后的坐标是否在范围内
             if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
                 rgb[0] = (int) bilinearInterpolation(rs, srcX, srcY);
                 rgb[1] = (int) bilinearInterpolation(gs, srcX, srcY);
                 rgb[2] = (int) bilinearInterpolation(bs, srcX, srcY);
                 destImage.setRGB(x, y, ImageUtil.encodeColor(rgb));
             } else {
                 rgb[0] = 255; 
                 rgb[1] = 255; 
                 rgb[2] = 255;
                 destImage.setRGB(x, y, ImageUtil.encodeColor(rgb));
             }
         }
     }
     return destImage;
 }

 public static float bilinearInterpolation(int[][] rs, int x, int y) {
	    int width = rs.length;
	    int height = rs[0].length;

	    // 计算插值所需的四个点的坐标
	    int x1 = Math.max(0, x - 1);
	    int x2 = Math.min(width - 1, x + 1);
	    int y1 = Math.max(0, y - 1);
	    int y2 = Math.min(height - 1, y + 1);

	    // 获取四个点的数值
	    float q11 = rs[x1][y1];
	    float q12 = rs[x1][y2];
	    float q21 = rs[x2][y1];
	    float q22 = rs[x2][y2];

	    // 计算插值权重
	    float dx = (float) (x - x1);
	    float dy = (float) (y - y1);

	    // 使用双线性插值公式计算新点的值
	    float interpolatedValue = (q11 * (1 - dx) * (1 - dy) +
	                               q21 * dx * (1 - dy) +
	                               q12 * (1 - dx) * dy +
	                               q22 * dx * dy);

	    return interpolatedValue;
	}
 public static BufferedImage ImageCorrection(BufferedImage image1) {
	    int width = image1.getWidth();  
	    int height = image1.getHeight(); 
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];  
	    int rs[][] = new int[width][height]; 
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height]; 
	    for (int j = 0; j < height; j++) {  
	        for (int i = 0; i < width; i++) {  
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];   
	        }  
	    }
	    int width1=800,height1=600;
	    BufferedImage destImage=new BufferedImage(width1,height1,BufferedImage.TYPE_INT_RGB);
	    int[] rx={150, 334, 461, 375}; 
	    int[] ry={621, 431, 527, 646}; 
	    double[]k=new double[8];
	    k[0]=(rx[1]-rx[0])/(double)width1;
	    k[1]=(rx[3]-rx[0])/(double)height1;
	    k[2]=((rx[2]-rx[0])-(rx[1]-rx[0])-(rx[3]-rx[0]))/(double)(width1*height1);
	    k[3]=rx[0];
	    k[4]=(ry[1]-ry[0])/(double)width1;
	    k[5]=(ry[3]-ry[0])/(double)height1;
	    k[6]=((ry[2]-ry[0])-(ry[1]-ry[0])-(ry[3]-ry[0]))/(double)(width1*height1);
	    k[7]=ry[0];
	    for(int j=0;j<height1;j++)
	    	for(int i=0;i<width1;i++) {
	    		int x0=(int)(k[0]*i+k[1]*j+k[2]*j*i+k[3]); // 计算新图像中每个像素对应的原始图像中的横坐标
	            int y0=(int)(k[4]*i+k[5]*j+k[6]*i*j+k[7]); // 计算新图像中每个像素对应的原始图像中的纵坐标
	            if(x0>=0&&x0<width1&&y0>=0&&y0<height1) // 如果计算出的坐标在原始图像范围内
	            	destImage.setRGB(i,j,ImageUtil.encodeColor(rgb)); 
	    	}
	  
     return destImage;
	  
	}
 /*public static BufferedImage remake(BufferedImage originalimage){
    int wid1=originalimage.getWidth(); // 获取原始图像的宽度
    int height1=originalimage.getHeight(); // 获取原始图像的高度
    int wid=800; // 设置新图像的宽度
    int height=600; // 设置新图像的高度
    int[] rx={383, 2179, 2263, 375}; // 定义四个点的横坐标
    int[] ry={1581, 1010, 2620, 2844}; // 定义四个点的纵坐标
    double k1=(rx[1]-rx[0])/(double)wid; // 计算第一段斜率
    double k2=(rx[3]-rx[0])/(double)height; // 计算第二段斜率
    double k3=((rx[2]-rx[0])-(rx[1]-rx[0])-(rx[3]-rx[0]))/(double)(wid*height); // 计算第三段斜率
    double k4=rx[0]; // 计算第四段斜率
    double k5=(ry[1]-ry[0])/(double)wid; // 计算第五段斜率
    double k6=(ry[3]-ry[0])/(double)height; // 计算第六段斜率
    double k7=((ry[2]-ry[0])-(ry[1]-ry[0])-(ry[3]-ry[0]))/(double)(wid*height); // 计算第七段斜率
    double k8=ry[0]; // 计算第八段斜率
    BufferedImage processedimage=new BufferedImage(wid,height,BufferedImage.TYPE_INT_RGB); // 创建新的图像
    for(int i=0;i<height;i++){ // 遍历新图像的每一行
        for(int j=0;j<wid;j++){ // 遍历新图像的每一列
            int x0=(int)(k1*j+k2*i+k3*ixj+k4); // 计算新图像中每个像素对应的原始图像中的横坐标
            int y0=(int)(k5*j+k6*i+k7*ixj+k8); // 计算新图像中每个像素对应的原始图像中的纵坐标
            if(x0>=0&&x0<wid1&&y0>=0&&y0<height1) // 如果计算出的坐标在原始图像范围内
                processedimage.setRGB(j,i,originalimage.getRGB(x0,y0)); // 将原始图像中的像素值设置到新图像中
        }
    }
    return processedimage; // 返回处理后的图像
}
*/
   public static BufferedImage Imageprove1(BufferedImage image1) {
	    int width = image1.getWidth();  
	    int height = image1.getHeight(); 
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    int minrs=Integer.MAX_VALUE/2;
	    int mings=Integer.MAX_VALUE/2;
	    int minbs=Integer.MAX_VALUE/2;
	    int maxrs=Integer.MIN_VALUE/2;
	    int maxgs=Integer.MIN_VALUE/2;
	    int maxbs=Integer.MIN_VALUE/2;
	     for (int j = 0; j < height; j++) {
	         for (int i = 0; i < width; i++) {
	             ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	             rs[i][j] = rgb[0]; 
	             gs[i][j] = rgb[1]; 
	             bs[i][j] = rgb[2];
	             if(minrs>rs[i][j])
	            	 minrs=rs[i][j];
	             if(mings>gs[i][j])
	            	 mings=gs[i][j];
	             if(minbs>bs[i][j])
	            	 minbs=bs[i][j];
	             if(maxrs<rs[i][j])
	            	 maxrs=rs[i][j];
	             if(maxgs<gs[i][j])
	            	 maxgs=gs[i][j];
	             if(maxbs<bs[i][j])
	            	 maxbs=bs[i][j];
	         }
	     }
	     BufferedImage destImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	     double ar,br,ag,bg,ab,bb;
	     ar=255.0/(maxrs-minrs);
	     br=-minrs*255.0/(maxrs-minrs);
	     ag=255.0/(maxgs-mings);
	     bg=-mings*255.0/(maxgs-mings);
	     ab=255.0/(maxbs-minbs);
	     bb=-minbs*255.0/(maxbs-minbs);
	   
	     for (int j = 0; j < height; j++) {  
		        for (int i = 0; i < width; i++) {  
		            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值  
		            rs[i][j] =(int)(ar*rs[i][j]+br);
		            gs[i][j] =(int)(ag*gs[i][j]+bg); 
		            bs[i][j] =(int)(ab*bs[i][j]+bb);
		            int newrs=Math.max(0, Math.min(255, rs[i][j]));
		            int newgs=Math.max(0, Math.min(255, gs[i][j]));
		            int newbs=Math.max(0, Math.min(255, bs[i][j]));
		            rgb[0]=newrs;
		            rgb[1]=newgs;
		            rgb[2]=newbs;
		            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		        }  
		    }
	     return destImage;
 }
 /*public static BufferedImage Imageprove1(BufferedImage image1) {
	    int width = image1.getWidth();
	    int height = image1.getHeight();
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int grayValues[][] = new int[width][height];
	    int minGrayValue = Integer.MAX_VALUE;
	    int maxGrayValue = Integer.MIN_VALUE;

	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值
	            int grayValue = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]); // 计算灰度值
	            grayValues[i][j] = grayValue;
	            if (minGrayValue > grayValue)
	                minGrayValue = grayValue;
	            if (maxGrayValue < grayValue)
	                maxGrayValue = grayValue;
	        }
	    }

	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    double a = 255.0 / (maxGrayValue - minGrayValue);
	    double b = -minGrayValue * 255.0 / (maxGrayValue - minGrayValue);

	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            int grayValue = grayValues[i][j];
	            int newGrayValue = (int) (a * grayValue + b);
	            newGrayValue = Math.max(0, Math.min(255, newGrayValue)); // 确保像素值在0到255之间
	            rgb[0] = rgb[1] = rgb[2] = newGrayValue;
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }

	    return destImage;
	}
*/
/* public static BufferedImage Imageprove2(BufferedImage image1,float y) {
	  int width = image1.getWidth();
      int height = image1.getHeight();
      int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
      int rgb[] = new int[3];
      int grayValues[][] = new int[width][height];
      int minGrayValue = Integer.MAX_VALUE;
      int maxGrayValue = Integer.MIN_VALUE;

      for (int j = 0; j < height; j++) {
          for (int i = 0; i < width; i++) {
              ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值
              int grayValue = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]); // 计算灰度值
              grayValues[i][j] = grayValue;
              if (minGrayValue > grayValue)
                  minGrayValue = grayValue;
              if (maxGrayValue < grayValue)
                  maxGrayValue = grayValue;
          }
      }

      BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      double c = 255.0 / (Math.pow(maxGrayValue, y) - Math.pow(minGrayValue, y));
      for (int j = 0; j < height; j++) {
          for (int i = 0; i < width; i++) {
              int grayValue = grayValues[i][j];
              int newGrayValue = (int) (c * Math.pow(grayValue, y));
              newGrayValue = Math.max(0, Math.min(255, newGrayValue)); // 确保像素值在0到255之间
              rgb[0] = rgb[1] = rgb[2] = newGrayValue;
              destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
          }
      }

      return destImage;
}*/
   public static BufferedImage Imageprove2(BufferedImage image1,float y) {
	   int width = image1.getWidth();  
	    int height = image1.getHeight(); 
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    int minrs=Integer.MAX_VALUE/2;
	    int mings=Integer.MAX_VALUE/2;
	    int minbs=Integer.MAX_VALUE/2;
	    int maxrs=Integer.MIN_VALUE/2;
	    int maxgs=Integer.MIN_VALUE/2;
	    int maxbs=Integer.MIN_VALUE/2;
	     for (int j = 0; j < height; j++) {
	         for (int i = 0; i < width; i++) {
	             ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	             rs[i][j] = rgb[0]; 
	             gs[i][j] = rgb[1]; 
	             bs[i][j] = rgb[2]; 
	             if(minrs>rs[i][j])
	            	 minrs=rs[i][j];
	             if(mings>gs[i][j])
	            	 mings=gs[i][j];
	             if(minbs>bs[i][j])
	            	 minbs=bs[i][j];
	             if(maxrs<rs[i][j])
	            	 maxrs=rs[i][j];
	             if(maxgs<gs[i][j])
	            	 maxgs=gs[i][j];
	             if(maxbs<bs[i][j])
	            	 maxbs=bs[i][j];
	         }
	     }
	     BufferedImage destImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	     double cr,cg,cb;
	     cr=255.0/ (Math.pow(maxrs, y) - Math.pow(minrs, y));
	     cg=255.0/ (Math.pow(maxgs, y) - Math.pow(mings, y));
	     cb=255.0/ (Math.pow(maxbs, y) - Math.pow(minbs, y));
	     for (int j = 0; j < height; j++) {  
		        for (int i = 0; i < width; i++) {  
		            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);  
		            rs[i][j] =(int) (cr * Math.pow(rs[i][j], y));
		            gs[i][j] =(int) (cg * Math.pow(gs[i][j], y));
		            bs[i][j] =(int) (cb * Math.pow(bs[i][j], y));
		            int newrs=Math.max(0, Math.min(255, rs[i][j]));
		            int newgs=Math.max(0, Math.min(255, gs[i][j]));
		            int newbs=Math.max(0, Math.min(255, bs[i][j]));
		            rgb[0]=newrs;
		            rgb[1]=newgs;
		            rgb[2]=newbs;
		            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
		        }  
		    }
	     return destImage;
	}
   public static BufferedImage Imageprove3(BufferedImage image1) {//直方图均衡化
	    int width = image1.getWidth();  
	    int height = image1.getHeight(); 
	    int srcRGBs[] = image1.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int red[] = new int[256];
	    int green[] = new int[256];
	    int blue[] = new int[256];
	    
	    // 统计各个通道的像素值出现次数
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            red[rgb[0]]++;
	            green[rgb[1]]++;
	            blue[rgb[2]]++;
	        }
	    }
	    
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    
	    // 计算各个通道的累积百分比
	    double rsp[] = new double[256];
	    double gsp[] = new double[256];
	    double bsp[] = new double[256];
	    for (int i = 0; i < 256; i++) {
	        for (int j = 0; j <= i; j++) {
	            rsp[i] += (double) red[j] / (width * height);
	            gsp[i] += (double) green[j] / (width * height);
	            bsp[i] += (double) blue[j] / (width * height);
	        }
	    }

	    // 将累积百分比映射到 [0, 255] 的范围内
	    int nrp[] = new int[256];
	    int ngp[] = new int[256];
	    int nbp[] = new int[256];	 
	    for (int i = 0; i < 256; i++) {
	        nrp[i] = (int) (rsp[i] * 255 + 0.5);
	        ngp[i] = (int) (gsp[i] * 255 + 0.5);
	        nbp[i] = (int) (bsp[i] * 255 + 0.5);
	    }

	    // 更新目标图像的像素值
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); // 分解RGB值
	            rgb[0] = nrp[rgb[0]];
	            rgb[1] = ngp[rgb[1]];
	            rgb[2] = nbp[rgb[2]];
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	       
	        }
	    }

	    return destImage;
	}
   public static BufferedImage arithmetic3(BufferedImage srcImage) {//3*3
	   int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2];
	        }
	    }
	    int r,g,b;
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	if(i>0&&j>0&&i<width-1&&j<height-1) {
	        		r=(int)((rs[i][j]+rs[i-1][j-1]+rs[i][j-1]+rs[i+1][j-1]+rs[i-1][j]+rs[i+1][j]+rs[i-1][j+1]+rs[i][j+1]+rs[i+1][j+1])/9);
	        		g=(int)((gs[i][j]+gs[i-1][j-1]+gs[i][j-1]+gs[i+1][j-1]+gs[i-1][j]+gs[i+1][j]+gs[i-1][j+1]+gs[i][j+1]+gs[i+1][j+1])/9);
	        		b=(int)((bs[i][j]+bs[i-1][j-1]+bs[i][j-1]+bs[i+1][j-1]+bs[i-1][j]+bs[i+1][j]+bs[i-1][j+1]+bs[i][j+1]+bs[i+1][j+1])/9);
	        		rs[i][j]=r;
	        		gs[i][j]=g;
	        		bs[i][j]=b;
	        	}
	        	rgb[0]=rs[i][j];
	        	rgb[1]=gs[i][j];
	        	rgb[2]=bs[i][j];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        }
	    }
	    return destImage;
   }
   public static BufferedImage arithmetic5(BufferedImage srcImage) {//5*5
	   int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];
	        }
	    }
	    int r,g,b;
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for(int j=0;j<height;j++) {
	    	for(int i=0;i<width;i++) {
	    		if(i>2&&i<width-2&&j>2&&j<height-2) {
	    			r=(int)((rs[i-2][j-2]+rs[i-1][j-2]+rs[i][j-2]+rs[i+1][j-2]+rs[i+2][j-2]+
	    					rs[i-2][j-1]+rs[i-1][j-1]+rs[i][j-1]+rs[i+1][j-1]+rs[i+2][j-1]+
	    					rs[i-2][j]+rs[i-1][j]+rs[i][j]+rs[i+1][j]+rs[i+2][j]+
	    					rs[i-2][j+1]+rs[i-1][j+1]+rs[i][j+1]+rs[i+1][j+1]+rs[i+2][j+1]+
	    					rs[i-2][j+2]+rs[i-1][j+2]+rs[i][j+2]+rs[i+1][j+2]+rs[i+2][j+2]
	    					)/25);
	    			g=(int)((gs[i-2][j-2]+gs[i-1][j-2]+gs[i][j-2]+gs[i+1][j-2]+gs[i+2][j-2]+
	    					gs[i-2][j-1]+gs[i-1][j-1]+gs[i][j-1]+gs[i+1][j-1]+gs[i+2][j-1]+
	    					gs[i-2][j]+gs[i-1][j]+gs[i][j]+gs[i+1][j]+gs[i+2][j]+
	    					gs[i-2][j+1]+gs[i-1][j+1]+gs[i][j+1]+gs[i+1][j+1]+gs[i+2][j+1]+
	    					gs[i-2][j+2]+gs[i-1][j+2]+gs[i][j+2]+gs[i+1][j+2]+gs[i+2][j+2]
	    					)/25);
	    			b=(int)((bs[i-2][j-2]+bs[i-1][j-2]+bs[i][j-2]+bs[i+1][j-2]+bs[i+2][j-2]+
	    					bs[i-2][j-1]+bs[i-1][j-1]+bs[i][j-1]+bs[i+1][j-1]+bs[i+2][j-1]+
	    					bs[i-2][j]+bs[i-1][j]+bs[i][j]+bs[i+1][j]+bs[i+2][j]+
	    					bs[i-2][j+1]+bs[i-1][j+1]+bs[i][j+1]+bs[i+1][j+1]+bs[i+2][j+1]+
	    					bs[i-2][j+2]+bs[i-1][j+2]+bs[i][j+2]+bs[i+1][j+2]+bs[i+2][j+2]
	    					)/25);
	    			rs[i][j]=r;
	        		gs[i][j]=g;
	        		bs[i][j]=b;
	    		}
	    		rgb[0]=rs[i][j];
	        	rgb[1]=gs[i][j];
	        	rgb[2]=bs[i][j];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	    	}
	    }
	    return destImage;
   }
   public static BufferedImage Weightarithmetic(BufferedImage srcImage) {
	   int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int r,g,b;
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	if(i>0&&j>0&&i<width-1&&j<height-1) {
	        		r=(int)((rs[i][j]*0.2+rs[i-1][j-1]*0.1+rs[i][j-1]*0.1+rs[i+1][j-1]*0.1+rs[i-1][j]*0.1+rs[i+1][j]*0.1+rs[i-1][j+1]*0.1+rs[i][j+1]*0.1+rs[i+1][j+1]*0.1));
	        		g=(int)((gs[i][j]*0.2+gs[i-1][j-1]*0.1+gs[i][j-1]*0.1+gs[i+1][j-1]*0.1+gs[i-1][j]*0.1+gs[i+1][j]*0.1+gs[i-1][j+1]*0.1+gs[i][j+1]*0.1+gs[i+1][j+1]*0.1));
	        		b=(int)((bs[i][j]*0.2+bs[i-1][j-1]*0.1+bs[i][j-1]*0.1+bs[i+1][j-1]*0.1+bs[i-1][j]*0.1+bs[i+1][j]*0.1+bs[i-1][j+1]*0.1+bs[i][j+1]*0.1+bs[i+1][j+1]*0.1));
	        		rs[i][j]=r;
	        		gs[i][j]=g;
	        		bs[i][j]=b;
	        	}
	        	rgb[0]=rs[i][j];
	        	rgb[1]=gs[i][j];
	        	rgb[2]=bs[i][j];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        }
	    }
	    return destImage;
   }
   public static BufferedImage midaverage(BufferedImage srcImage) {//中值滤波
	   int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];
	        }
	    }
	    int[]r=new int[9];
	    int[]g=new int[9];
	    int[]b=new int[9];	    
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	if(i>0&&j>0&&i<width-1&&j<height-1) {
	        	r[0]=rs[i][j];r[1]=rs[i-1][j-1];r[2]=rs[i][j-1];r[3]=rs[i+1][j-1];r[4]=rs[i-1][j];r[5]=rs[i+1][j];r[6]=rs[i-1][j+1];r[7]=rs[i][j+1];r[8]=rs[i+1][j+1];
	        	g[0]=gs[i][j];g[1]=gs[i-1][j-1];g[2]=gs[i][j-1];g[3]=gs[i+1][j-1];g[4]=gs[i-1][j];g[5]=gs[i+1][j];g[6]=gs[i-1][j+1];g[7]=gs[i][j+1];g[8]=gs[i+1][j+1];
	        	b[0]=bs[i][j];b[1]=bs[i-1][j-1];b[2]=bs[i][j-1];b[3]=bs[i+1][j-1];b[4]=bs[i-1][j];b[5]=bs[i+1][j];b[6]=bs[i-1][j+1];b[7]=bs[i][j+1];b[8]=bs[i+1][j+1];
	        	Arrays.sort(r);
	        	Arrays.sort(g);
	        	Arrays.sort(b);
	        	rs[i][j]=r[4];
	        	gs[i][j]=g[4];
	        	bs[i][j]=b[4];
	        	}
	        	rgb[0]=rs[i][j];
	        	rgb[1]=gs[i][j];
	        	rgb[2]=bs[i][j];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        }
	    }
	    return destImage;
   }
   public static BufferedImage arithmeticx(BufferedImage srcImage, int x) {//x*x
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int[] srcRGBs = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int[] rgb = new int[3];
	    int[][] rs = new int[width][height];
	    int[][] gs = new int[width][height];
	    int[][] bs = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];
	        }
	    }
	    int r, g, b;
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    x=x/2;
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            int count = 0;
	            r = g = b = 0;
	            for (int k = -x; k <= x; k++) {
	                for (int l = -x; l <= x; l++) {
	                    if (i + k >= 0 && i + k < width && j + l >= 0 && j + l < height) {
	                        r += rs[i + k][j + l];
	                        g += gs[i + k][j + l];
	                        b += bs[i + k][j + l];
	                        count++;
	                    }
	                }
	            }
	            r /= count;
	            g /= count;
	            b /= count;
	            rs[i][j] = r;
	            gs[i][j] = g;
	            bs[i][j] = b;
	            rgb[0] = rs[i][j];
	            rgb[1] = gs[i][j];
	            rgb[2] = bs[i][j];
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    return destImage;
	}
   public static BufferedImage midaveragex(BufferedImage srcImage, int x) {//x*x
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];
	        }
	    }
	    int[] r = new int[x * x];
	    int[] g = new int[x * x];
	    int[] b = new int[x * x];
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            if (i >= x / 2 && j >= x / 2 && i < width - x / 2 && j < height - x / 2) {
	                int k = 0;
	                for (int m = -x / 2; m <= x / 2; m++) {
	                    for (int n = -x / 2; n <= x / 2; n++) {
	                        r[k] = rs[i + m][j + n];
	                        g[k] = gs[i + m][j + n];
	                        b[k] = bs[i + m][j + n];
	                        k++;
	                    }
	                }
	                Arrays.sort(r);
	                Arrays.sort(g);
	                Arrays.sort(b);
	                rs[i][j] = r[x * x / 2];
	                gs[i][j] = g[x * x / 2];
	                bs[i][j] = b[x * x / 2];
	            } else {
	                // 对边界像素进行中值滤波处理
	                int count = 0;
	                for (int m = Math.max(0, i - x / 2); m <= Math.min(width - 1, i + x / 2); m++) {
	                    for (int n = Math.max(0, j - x / 2); n <= Math.min(height - 1, j + x / 2); n++) {
	                        r[count] = rs[m][n];
	                        g[count] = gs[m][n];
	                        b[count] = bs[m][n];
	                        count++;
	                    }
	                }
	                Arrays.sort(r);
	                Arrays.sort(g);
	                Arrays.sort(b);
	                rs[i][j] = r[count / 2];
	                gs[i][j] = g[count / 2];
	                bs[i][j] = b[count / 2];
	            }
	            rgb[0] = rs[i][j];
	            rgb[1] = gs[i][j];
	            rgb[2] = bs[i][j];
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    return destImage;
	}
   public static BufferedImage laplaceedge(BufferedImage srcImage) {
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width+2][height+2];
	    int gs[][] = new int[width+2][height+2];
	    int bs[][] = new int[width+2][height+2];
	    for (int j = 1; j < height+1; j++) {
	        for (int i = 1; i < width+1; i++) {
	            ImageUtil.decodeColor(srcRGBs[(j-1) * width + i-1], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2];
	        }
	    }    
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 1; j < height+1; j++) {
	        for (int i = 1; i < width+1; i++) {
	            rgb[0]=(int)((rs[i][j]*(8)+rs[i-1][j-1]*(-1)+rs[i][j-1]*(-1)+rs[i+1][j-1]*(-1)+rs[i-1][j]*(-1)+rs[i+1][j]*(-1)+rs[i-1][j+1]*(-1)+rs[i][j+1]*(-1)+rs[i+1][j+1]*(-1))/2);
	            rgb[1]=(int)((gs[i][j]*(8)+gs[i-1][j-1]*(-1)+gs[i][j-1]*(-1)+gs[i+1][j-1]*(-1)+gs[i-1][j]*(-1)+gs[i+1][j]*(-1)+gs[i-1][j+1]*(-1)+gs[i][j+1]*(-1)+gs[i+1][j+1]*(-1))/2);
	            rgb[2]=(int)((bs[i][j]*(8)+bs[i-1][j-1]*(-1)+bs[i][j-1]*(-1)+bs[i+1][j-1]*(-1)+bs[i-1][j]*(-1)+bs[i+1][j]*(-1)+bs[i-1][j+1]*(-1)+bs[i][j+1]*(-1)+bs[i+1][j+1]*(-1))/2);
	            if(rgb[0]<0)
	            	rgb[0]=0;
	            else if(rgb[0]>255)
	            	rgb[0]=255;
	            if(rgb[1]<0)
	            	rgb[1]=0;
	            else if(rgb[1]>255)
	            	rgb[1]=255;
	            if(rgb[2]<0)
	            	rgb[2]=0;
	            else if(rgb[2]>255)
	            	rgb[2]=255;
	            destImage.setRGB(i-1, j-1, ImageUtil.encodeColor(rgb));
	        }
	    }    
	    return destImage;	    
   }
 
   public static BufferedImage laplaceprove(BufferedImage srcImage,int k) {
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    
	    // 将图像RGB值存储到二维数组中
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[(j) * width + i], rgb);
	            rs[i][j] = rgb[0];
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2];
	        }
	    }
	    
	    // 应用拉普拉斯算子增强图像
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            // 边界处理
	            if (i >= 1 && j >= 1 && i < width - 1 && j < height - 1) {
	                rgb[0] = (int)(rs[i][j] * k + rs[i][j-1] * (-1) + rs[i-1][j] * (-1) + rs[i+1][j] * (-1) + rs[i][j+1] * (-1));
	                rgb[1] = (int)(gs[i][j] * k + gs[i][j-1] * (-1) + gs[i-1][j] * (-1) + gs[i+1][j] * (-1) + gs[i][j+1] * (-1));
	                rgb[2] = (int)(bs[i][j] * k + bs[i][j-1] * (-1) + bs[i-1][j] * (-1) + bs[i+1][j] * (-1) + bs[i][j+1] * (-1));
	                
	                // 灰度值处理
	                rgb[0] = Math.min(Math.max(rgb[0], 0), 255);
	                rgb[1] = Math.min(Math.max(rgb[1], 0), 255);
	                rgb[2] = Math.min(Math.max(rgb[2], 0), 255);
	            } else {
	                rgb[0] = rs[i][j];
	                rgb[1] = gs[i][j];
	                rgb[2] = bs[i][j];
	            }
	            
	            // 将处理后的像素值存储到目标图像中
	            destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    
	    return destImage;
	}
   public static BufferedImage Edge(BufferedImage srcImage,int x) {
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];	   
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    if(x==1) {//Sobel算子
	    	int rs[][] = new int[width][height];
	        int gs[][] = new int[width][height];
	        int bs[][] = new int[width][height];
	        for (int j = 0; j < height; j++) {
	            for (int i = 0; i < width; i++) {
	                ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	                rs[i][j] = rgb[0]; 
	                gs[i][j] = rgb[1]; 
	                bs[i][j] = rgb[2];
	            }
	        }    
	        for (int j = 1; j < height - 1; j++) {
	            for (int i = 1; i < width - 1; i++) {
	                int gxR = -rs[i-1][j-1] + rs[i+1][j-1] - 2*rs[i-1][j] + 2*rs[i+1][j] - rs[i-1][j+1] + rs[i+1][j+1];
	                int gyR = rs[i-1][j-1] + 2*rs[i][j-1] + rs[i+1][j-1] - rs[i-1][j+1] - 2*rs[i][j+1] - rs[i+1][j+1];
	                int gxG = -gs[i-1][j-1] + gs[i+1][j-1] - 2*gs[i-1][j] + 2*gs[i+1][j] - gs[i-1][j+1] + gs[i+1][j+1];
	                int gyG = gs[i-1][j-1] + 2*gs[i][j-1] + gs[i+1][j-1] - gs[i-1][j+1] - 2*gs[i][j+1] - gs[i+1][j+1];
	                int gxB = -bs[i-1][j-1] + bs[i+1][j-1] - 2*bs[i-1][j] + 2*bs[i+1][j] - bs[i-1][j+1] + bs[i+1][j+1];
	                int gyB = bs[i-1][j-1] + 2*bs[i][j-1] + bs[i+1][j-1] - bs[i-1][j+1] - 2*bs[i][j+1] - bs[i+1][j+1];
	                
	                int gradR = (int)Math.sqrt(gxR*gxR + gyR*gyR);
	                int gradG = (int)Math.sqrt(gxG*gxG + gyG*gyG);
	                int gradB = (int)Math.sqrt(gxB*gxB + gyB*gyB);
	                
	                if(gradR > 255) gradR = 255;
	                if(gradG > 255) gradG = 255;
	                if(gradB > 255) gradB = 255;
	                
	                rgb[0] = gradR;
	                rgb[1] = gradG;
	                rgb[2] = gradB;
	                
	                destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	            }
	        }    
	    }else if(x==2) {//Prewitt算子
	    	int rs[][] = new int[width][height];
	        int gs[][] = new int[width][height];
	        int bs[][] = new int[width][height];
	        for (int j = 0; j < height; j++) {
	            for (int i = 0; i < width; i++) {
	                ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	                rs[i][j] = rgb[0];
	                gs[i][j] = rgb[1]; 
	                bs[i][j] = rgb[2]; 
	            }
	        }    
	        for (int j = 1; j < height - 1; j++) {
	            for (int i = 1; i < width - 1; i++) {
	                int gxR = -rs[i-1][j-1] - rs[i][j-1] - rs[i+1][j-1] + rs[i-1][j+1] + rs[i][j+1] + rs[i+1][j+1];
	                int gyR = rs[i-1][j-1] + rs[i-1][j] + rs[i-1][j+1] - rs[i+1][j-1] - rs[i+1][j] - rs[i+1][j+1];
	                int gxG = -gs[i-1][j-1] - gs[i][j-1] - gs[i+1][j-1] + gs[i-1][j+1] + gs[i][j+1] + gs[i+1][j+1];
	                int gyG = gs[i-1][j-1] + gs[i-1][j] + gs[i-1][j+1] - gs[i+1][j-1] - gs[i+1][j] - gs[i+1][j+1];
	                int gxB = -bs[i-1][j-1] - bs[i][j-1] - bs[i+1][j-1] + bs[i-1][j+1] + bs[i][j+1] + bs[i+1][j+1];
	                int gyB = bs[i-1][j-1] + bs[i-1][j] + bs[i-1][j+1] - bs[i+1][j-1] - bs[i+1][j] - bs[i+1][j+1];
	                
	                int gradR = (int)Math.sqrt(gxR*gxR + gyR*gyR);
	                int gradG = (int)Math.sqrt(gxG*gxG + gyG*gyG);
	                int gradB = (int)Math.sqrt(gxB*gxB + gyB*gyB);
	                
	                if(gradR > 255) gradR = 255;
	                if(gradG > 255) gradG = 255;
	                if(gradB > 255) gradB = 255;
	                
	                rgb[0] = gradR;
	                rgb[1] = gradG;
	                rgb[2] = gradB;
	                
	                destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	            }
	        }    
	    }else if(x==3) {//Roberts算子
	    	int rs[][] = new int[width+2][height+2];
	        int gs[][] = new int[width+2][height+2];
	        int bs[][] = new int[width+2][height+2];
	        for (int j = 0; j < height; j++) {
	            for (int i = 0; i < width; i++) {
	                ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	                rs[i+1][j+1] = rgb[0]; 
	                gs[i+1][j+1] = rgb[1]; 
	                bs[i+1][j+1] = rgb[2];
	            }
	        }    
	        for (int j = 1; j < height+1; j++) {
	            for (int i = 1; i < width+1; i++) {
	                int rGrad = (int)(Math.abs(rs[i][j] - rs[i+1][j+1]) + Math.abs(rs[i+1][j] - rs[i][j+1]));
	                int gGrad = (int)(Math.abs(gs[i][j] - gs[i+1][j+1]) + Math.abs(gs[i+1][j] - gs[i][j+1]));
	                int bGrad = (int)(Math.abs(bs[i][j] - bs[i+1][j+1]) + Math.abs(bs[i+1][j] - bs[i][j+1]));
	                int grad = (int)(Math.sqrt(rGrad*rGrad + gGrad*gGrad + bGrad*bGrad));
	                if(grad > 255) grad = 255;
	                rgb[0] = grad;
	                rgb[1] = grad;
	                rgb[2] = grad;
	                destImage.setRGB(i-1, j-1, ImageUtil.encodeColor(rgb));
	            }
	        }    
	    }
	    return destImage;
   }
   public static BufferedImage FFTimage(BufferedImage srcImage) {
	    int iw = srcImage.getWidth(); // 获取源图像的宽度
	    int ih = srcImage.getHeight(); // 获取源图像的高度
	    int[] pixels = new int[iw * ih]; // 创建一个数组用于存储源图像的像素值
	    int[] newPixels; // 创建一个新的数组用于存储处理后的像素值
	    srcImage.getRGB(0, 0, iw, ih, pixels, 0, iw); // 将源图像的像素值存入数组
	    int w=1; // 初始化宽度
	    int h=1; // 初始化高度
	    while(w*2<=iw) { // 计算宽度的最小2的幂次方
	        w*=2;
	    }
	    while(h*2<=ih) { // 计算高度的最小2的幂次方
	        h*=2;
	    }
	    Complex[] src = new Complex[h * w]; // 创建一个复数数组用于存储源图像的像素值
	    // 从左往右，从上往下排序
	    Complex[] dest = new Complex[h * w]; // 创建一个复数数组用于存储处理后的像素值
	    newPixels = new int[h * w]; // 初始化新像素数组
	    for (int i = 0; i < h; i++) { // 遍历高度
	        for (int j = 0; j < w; j++) { // 遍历宽度
	            // 初始化src,dest
	            dest[i * w + j] = new Complex(); // 初始化目标数组的元素
	            // 获取蓝色通道分量，也就是像素点的灰度值
	            src[i * w + j] = new Complex(pixels[i * iw + j] & 0xff, 0); // 初始化源数组的元素
	        }
	    }
	    for (int i = 0; i < h; i++) { // 遍历高度
	        Complex[] temp = new Complex[w]; // 创建一个临时复数数组
	        for (int k = 0; k < w; k++) { // 遍历宽度
	            temp[k] = src[i * w + k]; // 将源数组的元素存入临时数组
	        }
	        temp = FFT.fft(temp); // 对临时数组进行快速傅里叶变换

	        for (int k = 0; k < w; k++) { // 遍历宽度
	            dest[i * w + k] = temp[k]; // 将临时数组的元素存入目标数组
	        }
	    }
	    for (int i = 0; i < w; i++) { // 遍历宽度
	        Complex[] temp = new Complex[h]; // 创建一个临时复数数组
	        for (int k = 0; k < h; k++) { // 遍历高度
	            temp[k] = dest[k * w + i]; // 将目标数组的元素存入临时数组
	        }
	        temp = FFT.fft(temp); // 对临时数组进行快速傅里叶变换

	        for (int k = 0; k < h; k++) { // 遍历高度
	            dest[k * w + i] = temp[k]; // 将临时数组的元素存入目标数组
	        }
	    }
	    for (int i = 0; i < h; i++) { // 遍历高度
	        for (int j = 0; j < w; j++) { // 遍历宽度
	            // 从左到右，从上到下遍历
	            double re = dest[i * w + j].re; // 获取实部
	            double im = dest[i * w + j].im; // 获取虚部

	            int ii = 0, jj = 0; // 初始化新的坐标
	            // 缩小数值，方便展示
	            int temp = (int) (Math.sqrt(re * re + im * im) / 100); // 计算新的像素值

	            // 用十字将图像切割为4等分，然后每个部分旋转180度，再拼接起来，方便观察
	            if (i < h / 2) { // 如果当前行数小于总行数的一半
	                ii = i + h / 2; // 新的行数为当前行数加上总行数的一半
	            } else { // 如果当前行数大于等于总行数的一半
	                ii = i - h / 2; // 新的行数为当前行数减去总行数的一半
	            }
	            if (j < w / 2) { // 如果当前列数小于总列数的一半
	                jj = j + w / 2; // 新的列数为当前列数加上总列数的一半
	            } else { // 如果当前列数大于等于总列数的一半
	                jj = j - w / 2; // 新的列数为当前列数减去总列数的一半
	            }
	            newPixels[ii * w + jj] = (clamp(temp) << 16) | (clamp(temp) << 8) | clamp(temp); // 设置新的像素值
	        }
	    }

	    BufferedImage destImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY); // 创建一个新的BufferedImage对象
	    destImg.setRGB(0, 0, w, h, newPixels, 0, w); // 将新的像素值设置到新的BufferedImage对象中

	    return destImg; // 返回新的BufferedImage对象
	}

   public static BufferedImage FFTimagerecover(BufferedImage srcImage) {
	    // 获取源图像的宽度和高度
	    int iw = srcImage.getWidth();
	    int ih = srcImage.getHeight();
	    // 创建一个整型数组，用于存储源图像的像素值
	    int[] pixels = new int[iw * ih];
	    // 创建一个整型数组，用于存储处理后的像素值
	    int[] newPixels;
	    // 将源图像的像素值存入数组
	    srcImage.getRGB(0, 0, iw, ih, pixels, 0, iw);

	    // 初始化宽度和高度为1
	    int w = 1;
	    int h = 1;
	    // 计算进行傅立叶变换的宽度和高度（2的整数次方）
	    while (w * 2 <= iw) {
	        w *= 2;
	    }
	    while (h * 2 <= ih) {
	        h *= 2;
	    }
	    // 分配内存空间
	    // 从左往右，从上往下排序
	    Complex[] src = new Complex[h * w];
	    // 从左往右，从上往下排序
	    Complex[] dest = new Complex[h * w];
	    newPixels = new int[h * w];

	    // 初始化src和dest数组
	    for (int i = 0; i < h; i++) {
	        for (int j = 0; j < w; j++) {
	            dest[i * w + j] = new Complex();
	            src[i * w + j] = new Complex(pixels[i * iw + j] & 0xff, 0);
	        }
	    }

	    // 在y方向上进行快速傅立叶变换
	    for (int i = 0; i < h; i++) {
	        Complex[] temp = new Complex[w];
	        for (int k = 0; k < w; k++) {
	            temp[k] = src[i * w + k];
	        }
	        temp = FFT.fft(temp);

	        for (int k = 0; k < w; k++) {
	            dest[i * w + k] = temp[k];
	        }
	    }

	    // 对x方向进行傅立叶变换
	    for (int i = 0; i < w; i++) {
	        Complex[] temp = new Complex[h];
	        for (int k = 0; k < h; k++) {
	            temp[k] = dest[k * w + i];
	        }
	        temp = FFT.fft(temp);

	        for (int k = 0; k < h; k++) {
	            dest[k * w + i] = temp[k];
	        }
	    }

	    // 去除指定频率部分，然后还原为图像
	    int halfWidth = w / 2;
	    int halfHeight = h / 2;
	    // 比例越小，边界越突出
	    double res = 0.24;

	    // 比例越大，图片越模糊
	    double del = 0.93;
	    // 先对列进行逆傅里叶变换，并去除指定频率。和傅里叶变换顺序相反
	    for (int i = 0; i < w; i++) {
	        Complex[] temp = new Complex[h];
	        for (int k = 0; k < h; k++) {
	            // 去除低频率，保留边缘，因为频率图尚未做十字切割并分别旋转180度，所以此时图像dest[] 的四个角落是低频率，而中心部分是高频率
	            // 第一个点(0,0)不能删除，删掉后整张图片就变黑了
	            if (i == 0 && k == 0) {
	                temp[k] = dest[k * w + i];
	            } else if ((i < res * halfWidth && k < res * halfHeight) || (i < res * halfWidth && k > (1 - res) * halfHeight)
	                    || (i > (1 - res) * halfWidth && k < res * halfHeight) || (i > (1 - res) * halfWidth && k > (1 - res) * halfHeight)) {
	                temp[k] = new Complex(0, 0);
	            } else {
	                temp[k] = dest[k * w + i];
	            }

	            // 去除高频率，模糊图像。和去除低频率代码冲突，需要先屏蔽上面去除低频率的代码才能执行
	            /*if ((i > ((1-del)*halfWidth) && k > ((1-del)*halfHeight)) && (i <((1+del)*halfWidth) && k < ((1+del)*halfHeight))) {
	                temp[k] = new Complex(0, 0);
	            } else {
	                temp[k] = dest[k * w + i];
	            }*/
	        }
	        temp = FFT.ifft(temp);
	        for (int k = 0; k < h; k++) {
	            // 这边需要除以N，也就是temp[]的长度
	            dest[k * w + i].im = temp[k].im / h;
	            dest[k * w + i].re = temp[k].re / h;
	        }
	    }

	    // 再对行进行逆傅里叶变换
	    for (int i = 0; i < h; i++) {
	        Complex[] temp = new Complex[w];
	        for (int k = 0; k < w; k++) {
	            temp[k] = dest[i * w + k];
	        }
	        temp = FFT.ifft(temp);
	        for (int k = 0; k < w; k++) {
	            // 这边需要除以N，也就是temp[]的长度
	            dest[i * w + k].im = temp[k].im / w;
	            dest[i * w + k].re = temp[k].re / w;
	        }
	    }

	    // 将处理后的像素值存入新的数组，并进行灰度化处理
	    for (int i = 0; i < h; i++) {
	        for (int j = 0; j < w; j++) {
	            // 从左到右，从上到下遍历
	            double re = dest[i * w + j].re;
	            int temp = (int) re;
	            newPixels[i * w + j] = (clamp(temp) << 16) | (clamp(temp) << 8) | clamp(temp);
	        }
	    }

	    // 创建一个新的BufferedImage对象，并将处理后的像素值设置到该对象中
	    BufferedImage destImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
	    destImg.setRGB(0, 0, w, h, newPixels, 0, w);

	    return destImg;
	}
   private static int clamp(int value) {
		return value > 255 ? 255 : (Math.max(value, 0));
	}
   public static BufferedImage FFTimagelow(BufferedImage srcImage, double D0) {
	    int iw = srcImage.getWidth(); // 获取源图像的宽度
	    int ih = srcImage.getHeight(); // 获取源图像的高度
	    int[] pixels = new int[iw * ih]; // 创建一个数组用于存储源图像的像素值
	    srcImage.getRGB(0, 0, iw, ih, pixels, 0, iw); // 将源图像的像素值存入数组

	    // 计算宽度和高度的最小2的幂次方
	    int w = Integer.highestOneBit(iw - 1) << 1;
	    int h = Integer.highestOneBit(ih - 1) << 1;

	    Complex[] src = new Complex[h * w]; // 创建一个复数数组用于存储源图像的像素值
	    Complex[] dest = new Complex[h * w]; // 创建一个复数数组用于存储处理后的像素值

	    for (int i = 0; i < h; i++) { // 遍历高度
	        for (int j = 0; j < w; j++) { // 遍历宽度
	            // 初始化src,dest
	            dest[i * w + j] = new Complex(); // 初始化目标数组的元素
	            // 获取蓝色通道分量，也就是像素点的灰度值
	            src[i * w + j] = new Complex(pixels[i * iw + j] & 0xff, 0); // 初始化源数组的元素
	        }
	    }

	    // 进行快速傅里叶变换
	    for (int i = 0; i < h; i++) {
	        Complex[] temp = new Complex[w];
	        for (int k = 0; k < w; k++) {
	            temp[k] = src[i * w + k];
	        }
	        temp = FFT.fft(temp); // 对临时数组进行快速傅里叶变换
	        for (int k = 0; k < w; k++) {
	            dest[i * w + k] = temp[k];
	        }
	    }
	    for (int i = 0; i < w; i++) {
	        Complex[] temp = new Complex[h];
	        for (int k = 0; k < h; k++) {
	            temp[k] = dest[k * w + i];
	        }
	        temp = FFT.fft(temp); // 对临时数组进行快速傅里叶变换
	        for (int k = 0; k < h; k++) {
	            dest[k * w + i] = temp[k];
	        }
	    }

	    // 应用低通滤波器
	    for (int i = 0; i < h; i++) {
	        for (int j = 0; j < w; j++) {
	            double amplitude = Math.sqrt(dest[i * w + j].re * dest[i * w + j].re + dest[i * w + j].im * dest[i * w + j].im);
	            if (amplitude > D0) {
	                dest[i * w + j].re = 0; // 将高频分量置零
	                dest[i * w + j].im = 0;
	            }
	        }
	    }

	    // 进行傅里叶反变换
	    for (int i = 0; i < h; i++) {
	        Complex[] temp = new Complex[w];
	        for (int k = 0; k < w; k++) {
	            temp[k] = dest[i * w + k];
	        }
	        temp = FFT.ifft(temp); // 使用IFFT方法
	        for (int k = 0; k < w; k++) {
	            dest[i * w + k] = temp[k];
	        }
	    }
	    for (int i = 0; i < w; i++) {
	        Complex[] temp = new Complex[h];
	        for (int k = 0; k < h; k++) {
	            temp[k] = dest[k * w + i];
	        }
	        temp = FFT.ifft(temp); // 使用IFFT方法
	        for (int k = 0; k < h; k++) {
	            dest[k * w + i] = temp[k];
	        }
	    }

	    int[] newPixels = new int[iw * ih]; // 创建一个新的数组用于存储处理后的像素值
	    for (int i = 0; i < h; i++) { // 遍历高度
	        for (int j = 0; j < w; j++) { // 遍历宽度
	            // 从左到右，从上到下遍历
	            double re = dest[i * w + j].re; // 获取实部
	            double im = dest[i * w + j].im; // 获取虚部

	            // 缩小数值，方便展示
	            int temp = (int) (Math.sqrt(re * re + im * im) / 100); // 计算新的像素值
	            newPixels[i * iw + j] = (clamp(temp) << 16) | (clamp(temp) << 8) | clamp(temp); // 设置新的像素值
	        }
	    }

	    BufferedImage destImg = new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY); // 创建一个新的BufferedImage对象
	    destImg.setRGB(0, 0, iw, ih, newPixels, 0, iw); // 将新的像素值设置到新的BufferedImage对象中

	    return destImg; // 返回新的BufferedImage对象
	}
   public static BufferedImage colortogray(BufferedImage srcImage) {
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    
	    
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[(j) * width + i], rgb);
	            rs[i][j] = rgb[0];
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2];
	        }
	    }
	    //int grayValue = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]); // 计算灰度值
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	int grayValue = (int) (0.299 * rs[i][j] + 0.587 * gs[i][j] + 0.114 *bs[i][j]);
	        	rgb[0]=grayValue;
	        	rgb[1]=grayValue;
	        	rgb[2]=grayValue;
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        }
	        }
	    return destImage;
   }
   public static BufferedImage colorImageprove(BufferedImage srcImage) {
	   BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_INT_RGB);
       int wid1 = srcImage.getWidth();
       int height1 = srcImage.getHeight();
       int sreRGB[] = new int[wid1 * height1];
       int num[]=new int[256];
       int rgb[]=new int[3];
       srcImage.getRGB(0, 0, wid1, height1, sreRGB, 0, wid1);
       for (int i = 0; i < height1; i++) {
           for (int j = 0; j < wid1; j++) {
        	   ImageUtil.decodeColor(sreRGB[i * wid1 + j], rgb);
              double hsi[]=new double[3];
              rgbToHSI(rgb,hsi);
               rgb[2]= (int)(hsi[2] * 255);
               num[rgb[2]]++;
           }
       }
       double rate[] = new double[256];
       rate[0]= num[0] / (double) (wid1 * height1);
       for (int i = 1; i < 255; i++) {
           rate[i]= num[i]/ (double) (wid1 * height1) + rate[i - 1];
       }
       rate[255]=1;
       for(int x=0;x<wid1;x++){
           for(int y=0;y<height1;y++){
               if(x>=0&&x<wid1&&y>=0&&y<height1){
            	   ImageUtil.decodeColor(sreRGB[y*wid1+x],rgb);
            	   double hsi[]=new double[3];
                   rgbToHSI(rgb,hsi);
                   rgb[2]= (int)(rate[rgb[2]] * 255);
                   destImage.setRGB(x,y,ImageUtil.encodeColor(rgb));
               }

           }
       }


       return destImage;

	}
  public static  BufferedImage vectormid(BufferedImage srcImage) {//矢量中值滤波
	  int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int[][]r=new int[9][3];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	if(i>0&&j>0&&i<width-1&&j<height-1) {
	        	r[0][0]=rs[i][j];
	        	r[0][1]=gs[i][j];
	        	r[0][2]=bs[i][j];
	        	
	        	r[1][0]=rs[i-1][j-1];
	        	r[1][1]=gs[i-1][j-1];
	        	r[1][2]=bs[i-1][j-1];
	        	
	        	r[2][0]=rs[i][j-1];
	        	r[2][1]=gs[i][j-1];
	        	r[2][2]=bs[i][j-1];
	        	
	        	r[3][0]=rs[i+1][j-1];
	        	r[3][1]=gs[i+1][j-1];
	        	r[3][2]=bs[i+1][j-1];
	        	
	        	r[4][0]=rs[i-1][j];
	        	r[4][1]=gs[i-1][j];
	        	r[4][2]=bs[i-1][j];
	        	
	        	r[5][0]=rs[i+1][j];
	        	r[5][1]=gs[i+1][j];
	        	r[5][2]=bs[i+1][j];
	        	
	        	r[6][0]=rs[i-1][j+1];
	        	r[6][1]=gs[i-1][j+1];
	        	r[6][2]=bs[i-1][j+1];
	        	
	        	r[7][0]=rs[i][j+1];
	        	r[7][1]=gs[i][j+1];
	        	r[7][2]=bs[i][j+1];
	        	
	        	r[8][0]=rs[i+1][j+1];
	        	r[8][1]=gs[i+1][j+1];
	        	r[8][2]=bs[i+1][j+1];
	        	int t[]=new int[3];
	        	for(int e=0;e<9;e++) {
	        		t[0]+=r[e][0];
	        		t[1]+=r[e][1];
	        		t[2]+=r[e][2];
	        	}
	        	t[0]/=9;
	        	t[1]/=9;
	        	t[2]/=9;
	        	int l[]=new int[9];
	        	for(int e=0;e<9;e++) {
	        		l[e]=(Math.abs(r[e][0]-t[0])+Math.abs(r[e][1]-t[1])+Math.abs(r[e][2]-t[2]));
	        	}
	        	int min=Integer.MAX_VALUE;
	        	int mine=Integer.MAX_VALUE;
	        	for(int e=0;e<9;e++) {
	        		if(l[e]<min) {
	        			min=l[e];
	        			mine=e;
	        		}
	        	}
	        	rgb[0]=r[mine][0];
	        	rgb[1]=r[mine][1];
	        	rgb[2]=r[mine][2];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        	}
	        }
	    }
	    return destImage;
  }
  public static  BufferedImage scalarmid(BufferedImage srcImage) {//标量中值滤波
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb); 
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int[]r=new int[9];
	    int[]g=new int[9];
	    int[]b=new int[9];	    
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	        	if(i>0&&j>0&&i<width-1&&j<height-1) {
	        	r[0]=rs[i][j];r[1]=rs[i-1][j-1];r[2]=rs[i][j-1];r[3]=rs[i+1][j-1];r[4]=rs[i-1][j];r[5]=rs[i+1][j];r[6]=rs[i-1][j+1];r[7]=rs[i][j+1];r[8]=rs[i+1][j+1];
	        	g[0]=gs[i][j];g[1]=gs[i-1][j-1];g[2]=gs[i][j-1];g[3]=gs[i+1][j-1];g[4]=gs[i-1][j];g[5]=gs[i+1][j];g[6]=gs[i-1][j+1];g[7]=gs[i][j+1];g[8]=gs[i+1][j+1];
	        	b[0]=bs[i][j];b[1]=bs[i-1][j-1];b[2]=bs[i][j-1];b[3]=bs[i+1][j-1];b[4]=bs[i-1][j];b[5]=bs[i+1][j];b[6]=bs[i-1][j+1];b[7]=bs[i][j+1];b[8]=bs[i+1][j+1];
	        	Arrays.sort(r);
	        	Arrays.sort(g);
	        	Arrays.sort(b);
	        	rs[i][j]=r[4];
	        	gs[i][j]=g[4];
	        	bs[i][j]=b[4];
	        	}
	        	rgb[0]=rs[i][j];
	        	rgb[1]=gs[i][j];
	        	rgb[2]=bs[i][j];
	        	destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));	
	        }
	    }
	    return destImage;
  }
  public static  BufferedImage singlechannel(BufferedImage srcImage,int t) {//单通道阈值
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];
	    int grayValues[][] = new int[width][height];
	    //int grayValue = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]); // 计算灰度值
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	            rs[i][j] = rgb[0];
	            gs[i][j] = rgb[1];
	            bs[i][j] = rgb[2];
	            grayValues[i][j]=(int)(0.299*rs[i][j]+0.587*gs[i][j]+0.114*bs[i][j]);
	        }
	    }
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            if(grayValues[i][j]>t)
	            	grayValues[i][j]=255;
	            if(grayValues[i][j]<t)
	            	grayValues[i][j]=0;
	            rgb[0] = rgb[1] = rgb[2] = grayValues[i][j];
	              destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    return destImage;
  }
  public static  BufferedImage rgbchannel(BufferedImage srcImage,int t,int r0,int g0,int b0) {//rgb彩色阈值
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    int rs[][] = new int[width][height];
	    int gs[][] = new int[width][height];
	    int bs[][] = new int[width][height];   
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	            rs[i][j] = rgb[0]; 
	            gs[i][j] = rgb[1]; 
	            bs[i][j] = rgb[2]; 
	        }
	    }
	    int r,g,b;
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	           r=(int) Math.pow((double)(rs[i][j]-r0), 2);
	           g=(int) Math.pow((double)(gs[i][j]-g0), 2);
	           b=(int) Math.pow((double)(bs[i][j]-b0), 2);
	           double x=Math.sqrt((r+g+b));
	           if(x>t) {
	        	   rs[i][j]=128;
	        	   gs[i][j]=128;
	        	   bs[i][j]=128;
	           }
	           rgb[0]=rs[i][j];
	           rgb[1]=gs[i][j];
	           rgb[2]=bs[i][j];
	           destImage.setRGB(i, j, ImageUtil.encodeColor(rgb));
	        }
	    }
	    return destImage;
}
  public static BufferedImage hsichannel(BufferedImage srcImage, int t, int r0, int g0, int b0) {
	    int width = srcImage.getWidth();
	    int height = srcImage.getHeight();
	    int srcRGBs[] = srcImage.getRGB(0, 0, width, height, null, 0, width);
	    int rgb[] = new int[3];
	    double hsi[] = new double[3];
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            ImageUtil.decodeColor(srcRGBs[j * width + i], rgb);
	            // 将RGB转换为HSI
	            rgbToHSI(rgb, hsi);
	            // 对亮度（I）应用阈值
	            if (hsi[2] > t) {
	                // 将HSI设置为某个任意值，这里为0.5, 0.5, 0.5（灰色）
	                hsi[0] = 0.5;
	                hsi[1] = 0.5;
	                hsi[2] = 0.5;
	            }
	            // 将HSI转换回RGB
	            hsiToRGB(hsi, rgb);
	            srcRGBs[j * width + i] = ImageUtil.encodeColor(rgb);
	        }
	    }
	    BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    destImage.setRGB(0, 0, width, height, srcRGBs, 0, width);
	    return destImage;
	}

	public static void rgbToHSI(int[] rgb, double[] hsi) {
	    // 将RGB值归一化到0-1范围
	    double r = rgb[0] / 255.0;
	    double g = rgb[1] / 255.0;
	    double b = rgb[2] / 255.0;

	    // 计算最小值和最大值以及差值
	    double min = Math.min(Math.min(r, g), b);
	    double max = Math.max(Math.max(r, g), b);
	    double delta = max - min;

	    // 计算色调（Hue）
	    double h = 0;
	    if (delta == 0) {
	        h = 0;
	    } else if (max == r) {
	        h = 60 * (((g - b) / delta) % 6);
	    } else if (max == g) {
	        h = 60 * (((b - r) / delta) + 2);
	    } else if (max == b) {
	        h = 60 * (((r - g) / delta) + 4);
	    }
	    if (h < 0) h += 360;

	    // 计算饱和度（Saturation）
	    double s = (max == 0) ? 0 : (1 - (min / max));

	    // 计算亮度（Intensity）
	    double i = (r + g + b) / 3;

	    // 将HSI值存储到数组中
	    hsi[0] = h;
	    hsi[1] = s;
	    hsi[2] = i;
	}

	public static void hsiToRGB(double[] hsi, int[] rgb) {
	    // 获取HSI值
	    double h = hsi[0];
	    double s = hsi[1];
	    double i = hsi[2];

	    // 初始化RGB值
	    double r, g, b;

	    // 如果饱和度为0，则RGB值相等
	    if (s == 0) {
	        r = g = b = i;
	    } else {
	        // 调整色调值
	        if (h >= 360) h -= 360;
	        h /= 60;

	        // 获取扇区值
	        int sector = (int) Math.floor(h);
	        double f = h - sector;
	        double p = i * (1 - s);
	        double q = i * (1 - s * f);
	        double t = i * (1 - s * (1 - f));

	        // 根据扇区值计算RGB值
	        switch (sector) {
	            case 0: { r = i; g = t; b = p; }
	            case 1:{ r = q; g = i; b = p; }
	            case 2:{ r = p; g = i; b = t; }
	            case 3:{ r = p; g = q; b = i; }
	            case 4:{ r = t; g = p; b = i; }
	            default:{ r = i; g = p; b = q; }
	        }
	    }

	    // 将RGB值乘以255并存储到数组中
	    rgb[0] = (int) (r * 255);
	    rgb[1] = (int) (g * 255);
	    rgb[2] = (int) (b * 255);
	}


}

