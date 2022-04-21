import java.nio.file.*
import java.util.stream.Stream
import java.util.stream.Collectors

def dir = "/media/ext/Films"

def filenames = []

def getTuple(Path path) {

    String filename = path.getFileName().toString()
   
    String directoryName = filename.substring(0, filename.lastIndexOf("."))

    new Tuple(filename, directoryName)
}

try (Stream<Path> stream = Files.list(Paths.get(dir))) {
        filenames = stream
          .filter(file -> !Files.isDirectory(file))
          .map(this::getTuple)
          .collect(Collectors.toSet());
}

filenames.each { film ->

    Path directoryToCreate = Path.of(dir, film[1])    

    println "Directory to create : ${directoryToCreate}"

    // Files.createDirectory( Paths.of(dir, film[1]) )

    // Files.move( Paths.get(dir, film[0]) , Paths.get(dir, film[1], film[0]))
}

