import spock.lang.Specification

class MoveFilmsSpec extends Specification {

  def "run move single movie files into own directories"() {
    given:
    File file1 = new File(MoveFilmsSpec.class.getResource("processFilms.groovy").toURI())

    GroovyShell groovyShell = new GroovyShell()

    def script = groovyShell.parse(file1)

    script.run()
  }
}  