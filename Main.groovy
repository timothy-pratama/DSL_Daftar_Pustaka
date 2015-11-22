/**
 * Created by timothy.pratama on 22-Nov-15.
 */
def dslDefinition = new File('src/SDL/DaftarPustaka.groovy' ).text
def dslInput = new File('inputDSL.dsl' ).text
def script = """
${dslDefinition}
${dslInput}
"""
new GroovyShell().evaluate(script)