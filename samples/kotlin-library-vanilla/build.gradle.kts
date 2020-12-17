import gradle.util.publishingToS3

plugins {
    id("common.kotlin-library")
}

// Publish to S3 maven repo
publishing {
    publishingToS3(this@publishing)
}