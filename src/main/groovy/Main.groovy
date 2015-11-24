import SDL.DaftarPustaka

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
//def dslDefinition = new DaftarPustaka()
def dslInput = this.getClass().getResourceAsStream( 'inputDSL.dsl' )

def dsl = ""
if (dslInput != null) {
    def input = new Scanner(dslInput).useDelimiter('\\A')
    dsl = input.hasNext() ? input.next() : null;
}

print dsl

/*def script = """
${dslDefinition}
${dslInput}
"""*/
//new GroovyShell().evaluate(script)