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

def moveSingleFilmsToDirectories(Path filmsPath) {

    println "Booting up process films"

    try (Stream<Path> stream = Files.list( filmsPath )) {
        filenames = stream
                .filter(file -> !Files.isDirectory(file))
                .map(this::getTuple)
                .collect(Collectors.toSet());
    }

    filenames.each { film ->

        Path directoryToCreate = filmsPath.resolve(film[1])
        Path fileSource = filmsPath.resolve(film[0])
        Path fileDestination = filmsPath.resolve(film[1]).resolve(film[0])

        println "Directory to create : ${directoryToCreate}"
        println "fileSource : ${fileSource}"
        println "fileDestination : ${fileDestination}"

//    Files.createDirectory( directoryToCreate )
//    Files.move( Paths.get(dir, film[0]) , Paths.get(dir, film[1], film[0]))
    }
}


