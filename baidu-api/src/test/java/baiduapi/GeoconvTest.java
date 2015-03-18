package baiduapi;

import com.yimin.javalibs.baiduapi.Geoconv;
import com.yimin.javalibs.baiduapi.Request;
import com.yimin.javalibs.baiduapi.ServiceOut;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by WuYimin on 2015/3/18.
 */
public class GeoconvTest {
    public static void main(String[] args) throws IOException {
        String ak = "G9nPi4eqOOCi7Kax1fPSBPQr";

        final Geoconv geoconv = Geoconv.on(ak).from(Geoconv.From.T3).to(Geoconv.To.F5).xml();

        geoconv.coord("39.918083", "116.425913").request(new Request() {
            @Override
            public ServiceOut perform(String url) throws IOException {
                System.out.printf("\nrequest url is %s\n",url);
                ServiceOut serviceOut = geoconv;
                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet(url));
                OutputStream output = serviceOut.getOuter();

                InputStream inputStream = httpResponse.getEntity().getContent();
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    output.write(bytes, 0, length);
                }
                return serviceOut;
            }
        });
    }
}
