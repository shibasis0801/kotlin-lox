package dev.shibasis.learn

enum class TokenType {
    // SINGLE CHARACTER TOKENS
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // SINGLE/DOUBLE CHARACTER TOKENS
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,

    // LITERALS
    IDENTIFIER, STRING, NUMBER,

    // KEYWORDS
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF

    ;

    companion object {
        val keywords = hashMapOf(
            "and" to AND,
            "class" to CLASS,
            "else" to ELSE,
            "false" to FALSE,
            "fun" to FUN,
            "for" to FOR,
            "if" to IF,
            "nil" to NIL,
            "or" to OR,
            "print" to PRINT,
            "return" to RETURN,
            "super" to SUPER,
            "this" to THIS,
            "true" to TRUE,
            "var" to VAR,
            "while" to WHILE
        )
    }
}