import edu.umn.gis.mapscript.*;


class Symbol_thread extends Thread {

    public String   mapName;
    public byte[]       resultBytes;

    public void run() {

        for (int i = 0; i < 50; i++ ) {
            mapObj map = new mapObj("");

            map.setName("Map Server");
            map.setProjection("init=epsg:4326");
            map.setExtent(-10,-10,10,10);
            map.setSize(256,256);               

            symbolObj symbol = new symbolObj("point","");
            symbol.setType( MS_SYMBOL_TYPE.MS_SYMBOL_ELLIPSE.swigValue() );
            symbol.setName("point");
            symbol.setFilled(1);

            lineObj l = new lineObj();
            l.add( new pointObj( 5, 5, 0 ) );
            symbol.setPoints(l);

            map.getSymbolset().appendSymbol(symbol);
            //symbol.delete(); // Remove java reference from symbol to avoid bug when the map is "deleted" before the symbol


            // Create the mapserver layer
            layerObj layer = new layerObj(map);
            layer.setName("symbol");
            layer.setProjection("init=epsg:4326");
            layer.setStatus(mapscript.MS_ON);
            layer.setType( MS_LAYER_TYPE.MS_LAYER_POINT );

            layer.setLabelitem("id");

            classObj cl = new classObj(layer);

            styleObj style = new styleObj(cl);
            style.setSymbol(1);
            style.setColor( new colorObj(255,0,0,255) );

            labelObj label = new labelObj();
            label.setColor( new colorObj(255,0,0,255) );
            label.setPosition( MS_POSITIONS_ENUM.MS_UC.swigValue() );

            cl.addLabel(label);


            shapeObj shape = new shapeObj( MS_SHAPE_TYPE.MS_SHAPE_POINT.swigValue() );

            lineObj line = new lineObj();
            line.add( new pointObj( 2, 3, 0.0 ) );

            shape.add(line);

            layer.addFeature(shape);

            imageObj img = map.draw();

            //System.out.println("Image size is: "+img.getSize());
            //System.out.println("Image size from getBytes is: "+img.getBytes().length);

            //img.delete();
            //map.delete();
        }
    }
}

public class Symbol {

    public static void main(String[] args)  {
            Symbol_thread tt[] = new Symbol_thread[100];
            int i;

            for( i = 0; i < tt.length; i++ )
            {
                tt[i] = new Symbol_thread();
            }

            for( i = 0; i < tt.length; i++ )
                tt[i].start();
    }
}