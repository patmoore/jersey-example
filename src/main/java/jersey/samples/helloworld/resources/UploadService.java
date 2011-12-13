/**
 * Copyright 2006-2011 by Amplafi. All rights reserved.
 * Confidential.
 */
package jersey.samples.helloworld.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

/**
 * @author patmoore
 *
 * NOTE: this is stateless ( so service object is created for each request )
 *
 */
@Path("/upload-service")
public class UploadService {
    // will not work stateless
//    int i = 0;
// @Context
// protected HttpServletResponse response;
// @Context
// protected HttpServletRequest request;

 public static final String WORKING = "/Users/patmoore/projects/jersey-examples/working/";

@POST
 @Consumes(MediaType.MULTIPART_FORM_DATA)
 @Produces(MediaType.APPLICATION_JSON)
 public String uploadFile(@PathParam("fileName") final String fileName,
   @FormDataParam("workgroupId") String workgroupId,
   @FormDataParam("userId") final int userId,
   @FormDataParam("content") final InputStream content)  {
  //.......Upload the file to S3 or netapp or any storage service
     try {
     String generateFileName = "received"+userId+".png";
    String pathname = WORKING + generateFileName;
    File file = new File(pathname);
     OutputStream output = new FileOutputStream(file);
     BufferedImage image = ImageIO.read(content);
     boolean success = ImageIO.write(image, "png", output);
     output.close();
     content.close();
     return generateFileName;
     } catch(Throwable e) {
         e.printStackTrace();
         return null;
     }
 }
}
