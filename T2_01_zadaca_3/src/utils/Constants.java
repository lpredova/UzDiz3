/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Josip
 */
public final class Constants {
    public static final String ANSI_ESC = "\033[";
    public static final String CURSOR_SAVE =  ANSI_ESC + "s";
    public static final String CURSOS_RESTORE = ANSI_ESC + "u";
    public static final String ERASE_END_OF_LINE = ANSI_ESC + "K";
    public static final String ERASE_START_OF_LINE = ANSI_ESC + "1K";
    public static final String ERASE_LINE = ANSI_ESC + "2K";
    public static final String ERASE_SCREEN = ANSI_ESC + "2J";
}
