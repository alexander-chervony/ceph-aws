#### En example of CEPH through AWS client usage 

This is a scala version, but the usage is very similar for other languages (well, at least it's the same for java).

Contains fixes for 2 issues:

- "com.amazonaws.services.s3.model.AmazonS3Exception: null (Service: Amazon S3; Status Code: 400; Error Code: InvalidArgument; Request ID: null), S3 Extended Request ID: null": see CephTests

- "non-hardcoded way of multiple user rigts grant (s3 bucket policies absence workaround)": see CephTests
