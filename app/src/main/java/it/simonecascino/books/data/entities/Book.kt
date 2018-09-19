package it.simonecascino.books.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books")
data class Book(

        @PrimaryKey val id: String,
        val title: String,
        val subtitle: String?,
        val authors: String,
        val publisher: String?,
        val publishedDate: String,
        val description: String?,
        val pageCount: Int,
        val categories: String,
        val smallThumbNail: String,
        val thumbnail: String


){

    companion object {

        const val ID = "id"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val AUTHORS = "authors"
        const val PUBLISHER = "publisher"
        const val PUBLISHED_DATE = "publishedDate"
        const val DESCRIPTION = "description"
        const val PAGE_COUNT = "pageCount"
        const val CATEGORIES = "categories"
        const val SMALL_THUMBNAIL = "smallThumbnail"
        const val THUMBNAIL = "thumbnail"
        const val IMAGE_LINKS = "imageLinks"
        const val VOLUME_INFO = "volumeInfo"

    }

}

/*

{

"kind": "books#volume",
"id": "jCbhV37Cpw8C",
"etag": "foWjoN8M2yE",
"selfLink": "https://www.googleapis.com/books/v1/volumes/jCbhV37Cpw8C",
"volumeInfo": {
    "title": "Quilting in Black and White",
    "authors": [
    "House of White Birches"
    ],
    "publisher": "Leisure Arts",
    "publishedDate": "2012",
    "description": "Black and white quilts have become a hot new trend for quilters of all ages. A seemingly simple pattern can be dramatically updated merely by adding a little black and white fabric into the design. The book includes projects that are completely black and white and also includes designs that have splashes of colour, or just splashes of black and white. Quilters will love the variety of designs, from an adorable cat wall hanging to a bold bed quilt made up of black, white and bright yellow fabrics. Techniques used include applique, patchwork and foundation piecing. Each design is shown in a lifestyle photograph, has an assembly diagram and is accompanied by a complete materials list and step-by-step instructions.",
    "industryIdentifiers": [
    {},
    {}
    ],
    "readingModes": {},
    "pageCount": 64,
    "printType": "BOOK",
    "categories": [],
    "averageRating": 5,
    "ratingsCount": 1,
    "maturityRating": "NOT_MATURE",
    "allowAnonLogging": false,
    "contentVersion": "0.6.0.0.preview.1",
    "imageLinks": {},
    "language": "en",
    "previewLink": "http://books.google.ch/books?id=jCbhV37Cpw8C&printsec=frontcover&dq=quilting&hl=&cd=1&source=gbs_api",
    "infoLink": "http://books.google.ch/books?id=jCbhV37Cpw8C&dq=quilting&hl=&source=gbs_api",
    "canonicalVolumeLink": "https://books.google.com/books/about/Quilting_in_Black_and_White.html?hl=&id=jCbhV37Cpw8C"
},
"saleInfo": {},
"accessInfo": {
    "country": "CH",
    "viewability": "PARTIAL",
    "embeddable": true,
    "publicDomain": false,
    "textToSpeechPermission": "ALLOWED",
    "epub": {
        "isAvailable": false
    },
    "pdf": {
        "isAvailable": false
    },
    "webReaderLink": "http://play.google.com/books/reader?id=jCbhV37Cpw8C&hl=&printsec=frontcover&source=gbs_api",
    "accessViewStatus": "SAMPLE",
    "quoteSharingAllowed": false
},
"searchInfo": {
    "textSnippet": "Black and white quilts have become a hot new trend for quilters of all ages."
}
}

 */