import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.FileSystem
import java.util.stream.Stream

class MoveFilmsSpec extends Specification {

  def setup() {
    // For a simple file system with Unix-style paths and behavior:
    FileSystem fs = Jimfs.newFileSystem(Configuration.unix());

    Path foo = fs.getPath("/blah");
    Files.createDirectory(foo);

//    Path foo = fs.getPath("/media/ext/Films/test");
//    Files.createDirectory(Path.of("/media"));
//    Files.createDirectory(Path.of("/media/ext"));
//    Files.createDirectory(Path.of("/media/ext/Films"));
  }

  def "run move single movie files into own directories"() {
    given:
    File file1 = new File(MoveFilmsSpec.class.getResource("processFilms.groovy").toURI())

    Path filmPath = Path.of("/media/ext/Films")

    GroovyShell groovyShell = new GroovyShell()

    def script = groovyShell.parse(file1)

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