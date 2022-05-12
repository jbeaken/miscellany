import spock.lang.Specification

class HelloSpockSpec extends Specification {


  def "length of Spock's and his friends' names"() {
    given:
    File resource = HelloSpockSpec.class.getResource("org/mzuri/media/processFilms.groovy").asFile()
    GroovyShell groovyShell = new GroovyShell()
    groovyShell.parse(resource)


  }
}  