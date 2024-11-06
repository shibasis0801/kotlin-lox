package dev.shibasis.learn

private const val NULL = '\u0000'
class Scanner(
    val source: String,
    val tokens: ArrayList<Token> = arrayListOf()
) {
    var start = 0
    var current = 0
    var line = 1

    private fun isAtEnd() = current >= source.length

    private fun advance() = source[current++]

    private fun addToken(type: TokenType, literal: Any? = null) {
        val lexeme = source.substring(start, current)
        tokens.add(Token(type, lexeme, literal, line))
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd() || source[current] != expected) return false
        current++
        return true
    }

    private fun peek(): Char = if (isAtEnd()) NULL else source[current]
    private fun peekNext(): Char = if (current + 1 >= source.length) NULL else source[current + 1]

    private fun isDigit(c: Char) = c in '0'..'9'
    private fun isAlpha(c: Char) = c in 'a'..'z' || c in 'A'..'Z' || c == '_'
    private fun isAlphaNumeric(c: Char) = isAlpha(c) || isDigit(c)

    private fun string() {
        while(peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated String")
            return
        }

        advance()

        val literal = source.substring(start + 1, current - 1)
        addToken(TokenType.STRING, literal)
    }

    private fun number() {
        while (isDigit(peek())) advance()

        // peekNext is needed to ensure that it is a fraction and not an extension function on a number
        if (peek() == '.' && isDigit(peekNext())) {
            advance()
            while (isDigit(peek())) advance()
        }

        val literal = source.substring(start, current).toDouble()
        addToken(TokenType.NUMBER, literal)
    }

    private fun identifier() {
        while(isAlphaNumeric(peek())) advance()

        val literal = source.substring(start, current)
        val type = TokenType.keywords[literal] ?: TokenType.IDENTIFIER

        addToken(type, literal)
    }

    private fun scanToken() {
        val c = advance()
        when(c) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            '!' -> addToken(if(match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if(match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if(match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '>' -> addToken(if(match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            '/' -> {
                if (match('/')) {
                    while(peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH)
                }
            }
            ' ', '\r', '\t' -> {}
            '\n' -> line++
            '"' -> string()
            else -> {
                if (isDigit(c)) {
                    number()
                } else if (isAlpha(c)) {
                    identifier()
                } else {
                    Lox.error(line, "Unexpected Character: $c")
                }
            }
        }
    }

    fun scanTokens(): List<Token> {
        while(!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

}