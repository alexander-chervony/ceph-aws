#### En example of CEPH through AWS client usage 

This is a scala version, but the usage is very similar for other languages (well, at least it's the same for java).

CephTests contains fix for:

- ```"com.amazonaws.services.s3.model.AmazonS3Exception: null (Service: Amazon S3; Status Code: 400; Error Code: InvalidArgument; Request ID: null), S3 Extended Request ID: null"```

As well as s3 bucket policies absence workaround for the scenario when it needs to share the uploaded file with other user(s):

- No-hardcode way of multiple user rigts grant.

To run the test define ```cephHost, bucketName, accessKey, secretKey``` and run ```sbt test```
