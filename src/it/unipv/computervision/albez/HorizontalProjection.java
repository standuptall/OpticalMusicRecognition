package it.unipv.computervision.albez;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HorizontalProjection {
    private final BufferedImage binaryImage;

    public HorizontalProjection(BufferedImage binaryImage){
        this.binaryImage = binaryImage;
    }

    /*
    Ritorna un array bidimensionale, composto dal numero di pentagrammi orizzontali nel foglio mentre l'altro array sono due elementi: il primo è coordinata verticale y e
    l'altro è la sua altezza (height)
     */
    public List<int[]> getStaff(){
        int[] horiz = new int[binaryImage.getHeight()];
        //costruisco immagine 1D
        int[] rgb = binaryImage.getRGB(0, 0, binaryImage.getWidth(), binaryImage.getHeight(), null, 0, binaryImage.getWidth());
        for(int i =0;i< rgb.length;i++){

            var riga = i / binaryImage.getWidth();
            if(rgb[i] == 0xFF000000)
                horiz[riga]++;
        }
        int c = 0;
        var ret = new ArrayList<int[]>();
        //adesso scandaglio l'arrray appena creato. Se è coperto per + del 70%, allora è una riga.
        int contatorePentagramma = 0;
        int[] currentElem = new int[2];
        for(int i =0;i<horiz.length;i++){
            if(((double)horiz[i] / binaryImage.getWidth()) > 0.7){
                contatorePentagramma++;
                if(currentElem[0] == 0)
                    currentElem[0]= i;
                //vado avanti fino al prossimo spazio bianco, magari la linea è spessa tanti pixel
                while(i<horiz.length && ((double)horiz[i] / binaryImage.getWidth()) > 0.7 )
                    i++;
                if(contatorePentagramma == 5) {
                    contatorePentagramma = 0;
                    currentElem[1] = i-currentElem[1]; //height
                    ret.add(currentElem);
                    currentElem = new int[2];
                }
            }
        }
        return ret;
    }


}
