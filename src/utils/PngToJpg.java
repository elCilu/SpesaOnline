package utils;

import dao.ProductDao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngToJpg {
    private static final File prodImg = new File("");
 /*   public static void main( String[] args){
        File pngImage = new File("/home/king_cheikh/Scrivania/prova.png");
        File jpgImage = new File("/home/king_cheikh/Scrivania/prova.jpg");
        changeExtension(pngImage, jpgImage);
        System.out.println("COnversion completed");
    }*/
    public static void changeExtension(){
        int size = ProductDao.getAllProducts().size();
        for(int i = 0; i < size; i++){
            File pngImage = new File( prodImg.getAbsolutePath() + "/images/" + "prod_" + String.format("%02d", i)  + ".png");
            File jpgImage = new File( prodImg.getAbsolutePath() + "/images/" + "prod_" + String.format("%02d", i) +  ".jpg");

            if(pngImage.exists() && !jpgImage.exists())
                try{
                    BufferedImage pngBuffer = ImageIO.read(pngImage);
                    BufferedImage jpgBuffer = new BufferedImage(pngBuffer.getWidth(), pngBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
                    jpgBuffer.createGraphics().drawImage(pngBuffer, 0, 0, Color.WHITE, null);
                    ImageIO.write(jpgBuffer, "jpg", jpgImage);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
        }


    }
}
