import gradle.util.publishingToS3

plugins {
    id("app.kotlin-library-conventions")
}

// Publish to S3 maven repo
publishing {
    publishingToS3(this@publishing)
}