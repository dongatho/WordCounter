import java.io.File

import scala.collection.mutable
import scala.io.Source.fromFile
import scala.io.StdIn.readLine
import mutable.HashMap

object WordCounter {

  /**
   * Count the number of occurrences of each word in a file
   *
   * @return a Map( word -> total occurrences )
   */
  def wordCount(filePath: String): Map[String, Int] = {
    val counter = fromFile(filePath)
      .getLines
      .flatMap(_.split("\\W+"))
      .foldLeft(Map.empty[String, Int]){
        (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
      }
    return counter
  }

  /**
   * For each word in @wordMap, this method calculates percentage based on word total
   *
   * @param wordMap Map( word -> total occurrences )
   * @param total amount of words
   * @return a Map( word -> percentage occurrences )
   */
  def getPercentageMap(wordMap:Map[String, Int], total: Int):Map[String, Float] = {

    val pctMap: Map[String, Float] = wordMap.map({
      case (x, d) =>
        x -> ( d * 100 )/total
    })

    return pctMap
  }

  /**
   * it calculates the total number of words by adding the values ​​of the myMap
   *
   * @param wordMap Map( word -> total occurrences )
   * @return total number of words
   */
  def getTotalWord(wordMap:Map[String, Int]):Int = {

    val total = wordMap.foldLeft(0)(_+_._2)

    return total
  }

  /**
   * Get all of allowed files from folder
   *
   * @param dir Folder Name
   * @param extensions type of file extensions allowed
   * @return list of files
   */
  def getListOfFiles(dir: File, extensions: List[String]): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file => extensions.exists(file.getName.endsWith(_)) }
  }

  /**
   *  Initial function
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("\nit's necessary a param (folder path)\n")
      return
    }

    // Get files from a path
    val path = args(0)
    val okFileExtensions = List("txt", "csv", "dat")
    val files = getListOfFiles(new File(path), okFileExtensions)

    val fileMap = HashMap[String, Map[String, Float]]()
    // Building a file Map ( fileName -> percentages of words in file  )
    for( fileName <- files){
      var wordMap = wordCount(fileName.getAbsolutePath)
      var total = getTotalWord(wordMap)

      // get a new map with percentage based on word total
      var percentageMap = getPercentageMap(wordMap, total)

      fileMap.addOne( fileName.getName, percentageMap )
    }

    var targetWord = ""
    do {
      targetWord = readLine("\nEnter target word or 'quit' to exit: ")

      val top_N = 10;

      // find all of files which contain the target word
      val filteredtMap = fileMap.filter( x => x._2.exists( y => y._1 == targetWord ) ).map({
        case (x, d) =>
          x -> d(targetWord)
      })

      if( filteredtMap.isEmpty ){
        println("\nno matches found")
      }else{
        println( "\nContaining the word \"" + targetWord + "\" : " + filteredtMap.size + " files." )
        println("Top files : ")

        // Sort map descending by percentage and take first N
        filteredtMap.toSeq.sortWith(_._2 > _._2).take(top_N).foreach {
          case (fileName, element) => { println(s"$fileName ==> $element %") }
        }
      }


    }while( targetWord != "quit" )

  }

}
