package br.com.davidbuzatto.jjudge.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class PostProcessingEnabler implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create( Gson gson, TypeToken<T> tt ) {
        
        TypeAdapter<T> delegate = gson.getDelegateAdapter( this, tt );
        
        return new TypeAdapter<T>() {
            
            @Override
            public void write( JsonWriter writer, T t ) throws IOException {
                delegate.write( writer, t );
            }

            @Override
            public T read( JsonReader reader ) throws IOException {
                
                T obj = delegate.read( reader );
                
                if ( obj instanceof PostProcessable ) {
                    ( (PostProcessable) obj ).gsonPostProcess();
                }
                
                return obj;
                
            }
            
        };
        
    }
    
}
