import java.io.File

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.{ClientConfiguration, Protocol}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FreeSpec, Matchers}


class CephTests extends FreeSpec with Matchers with ScalaFutures {

  val cephHost = ""
  val bucketName = ""
  val accessKey = ""
  val secretKey = ""

  "Scenario: Ceph RW should work" - {
    "Given an Amazon S3 client created for Ceph access " - {
      val client = createCephClient()
      "When file is written " - {
        val key = writeFileToCeph(client)
        "Then a file must exist in destination " in {
          client.getObject(bucketName, key).getObjectContent shouldNot be(null)
        }
      }
    }
  }

  def createCephClient(): AmazonS3Client = {
    val creds = new BasicAWSCredentials(accessKey, secretKey)
    val config = new ClientConfiguration().withProtocol(Protocol.HTTP)

    // fix for "com.amazonaws.services.s3.model.AmazonS3Exception: null (Service: Amazon S3; Status Code: 400; Error Code: InvalidArgument; Request ID: null), S3 Extended Request ID: null"
    // this force the client signer not to use v4 signin !
    // check AmazonS3Client.createSigner method and AmazonS3Client for signer type available
    config.setSignerOverride("S3SignerType")

    new AmazonS3Client(creds, config).withEndpoint(cephHost)
  }

  def writeFileToCeph(cephClient: AmazonS3Client): String = {
    val fileName = java.util.UUID.randomUUID.toString
    val tempFile = File.createTempFile(fileName, ".tmp")
    tempFile.deleteOnExit()
    val key = s"it/$fileName.tmp"
    cephClient.putObject(bucketName, key, tempFile)

    // This is a workaround for situation when multiple users need to access a file (since there is no bucket policies in ceph)
    // It allows not to hardcode different user credetials but rather replicate these creds that once were set for the bucket
    // The code works from bucket owner only:
    val acl = cephClient.getBucketAcl(bucketName)
    cephClient.setObjectAcl(bucketName, key, acl)

    key
  }
}

