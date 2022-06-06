import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.FileSystem
import java.util.stream.Stream

class MoveFilmsSpec extends Specification {

  FileSystem fs

  def setup() {
    // For a simple file system with Unix-style paths and behavior:
    fs = Jimfs.newFileSystem(Configuration.unix());

    Path mediaPath = fs.getPath("/media/");
    Path filmsPath = fs.getPath("/media/films");

    Files.createDirectory(mediaPath);
    Files.createDirectory(filmsPath);
  }

  def "run move single movie files into own directories"() {
    given: "script is loads"
    File file1 = new File(MoveFilmsSpec.class.getResource("processFilms.groovy").toURI())
    GroovyShell groovyShell = new GroovyShell()
    def script = groovyShell.parse(file1)

    and: "film path is mocked"
    Path filmPath =  fs.getPath("/media/films")

    and: "run script"
    script.moveSingleFilmsToDirectories(filmPath)
  }

  def "Should create a file on a file system"() {
    given:

    FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
    String fileName = "newFile.txt";
    Path pathToStore = fileSystem.getPath("");
    Path filePath = pathToStore.resolve(fileName);
    Files.createFile(filePath);

    expect:
    Files.exists( pathToStore.resolve(fileName) ) == true
    Files.exists( Path.of("/blah" )) == true
  }
}  