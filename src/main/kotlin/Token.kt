package dev.shibasis.learn

/* Lexeme is the raw string, Token is the lexeme + metadata */
data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?,
    val line: Int
) {
    override fun toString() = "$type $lexeme $literal"
}