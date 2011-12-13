import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import jersey.samples.helloworld.resources.UploadService;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.web.GrizzlyWebTestContainerFactory;
import org.testng.annotations.Test;

public class UploadServiceTest extends JerseyTest {
    public UploadServiceTest() {
        super(new WebAppDescriptor.Builder(UploadService.class.getPackage().getName()).contextPath("/public/rest").build());
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Test
    public void testUploadService() throws Exception {
        String fileName = "exploits_of_a_mom.png";

        int i = 0;
        do {
            WebResource webResource = resource();

            FormDataMultiPart form = new FormDataMultiPart();
            form.field("fileName", fileName);
            form.field("workgroupId", "XXX");
            form.field("userId", Integer.toString(i));
            InputStream content = new FileInputStream(new File(UploadService.WORKING+ fileName));
            FormDataBodyPart fdp = new FormDataBodyPart("content", content, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            form.bodyPart(fdp);

            String responseJson = webResource.path("upload-service").type(MediaType.MULTIPART_FORM_DATA).post(String.class, form);
            content.close();
            System.out.println(responseJson);
            fileName = responseJson;
        } while(i++<4);
    }
}