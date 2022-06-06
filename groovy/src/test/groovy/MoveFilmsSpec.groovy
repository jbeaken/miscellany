import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.FileSystem

class MoveFilmsSpec extends Specification {

  FileSystem fileSystem
  GroovyShell groovyShell = new GroovyShell()

  def setup() {
    fileSystem = Jimfs.newFileSystem(Configuration.unix());

    Path mediaPath = fileSystem.getPath("/media/");
    Path filmsPath = fileSystem.getPath("/media/films");
    Path film1Path = fileSystem.getPath("/media/films/film1");
    Path film2Path = fileSystem.getPath("/media/films/film2");

    Path film3Path = fileSystem.getPath("/media/films/film3.mp4");
    Path film4Path = fileSystem.getPath("/media/films/film4.mkv");

    Files.createDirectory(mediaPath);
    Files.createDirectory(filmsPath);
    Files.createDirectory(film1Path);
    Files.createDirectory(film2Path);

    Files.createFile(film3Path);
    Files.createFile(film4Path);
  }

  def "run move single movie files into own directories"() {
    given: "script is loads"
    File processFilmsScript = new File(MoveFilmsSpec.class.getResource("processFilms.groovy").toURI())
    def script = groovyShell.parse(processFilmsScript)

    and: "film path is mocked"
    Path filmPath =  fileSystem.getPath("/media/films")

    and: "run script"
    script.moveSingleFilmsToDirectories(filmPath)

    expect:
    Files.isDirectory( Path.of("/media/films/film3" )) == true
    Files.isDirectory( Path.of("/media/films/film4" )) == true

    and:
    Files.exists( Path.of("/media/films/film3/film3.mp4" )) == true
    Files.exists( Path.of("/media/films/film4/film4.mkv" )) == true

    Files.exists( Path.of("/media/films/film3.mp4" )) == false
    Files.exists( Path.of("/media/films/film4.mkv" )) == false

  }
}  