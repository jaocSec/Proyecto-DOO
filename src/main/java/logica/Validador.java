package logica;

import java.util.regex.Pattern;
/**
 * Clase utilitaria para validar los datos ingresados en los formularios.
 * * @author Antonia-FSR
 */
public class Validador {

    /**
     * Valida que el correo tenga un formato correcto (ejemplo@dominio.com)
     */
    public static boolean esCorreoValido(String correo) {
        // Expresión regular para correos estándar
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return Pattern.matches(regex, correo);
    }

    /**
     * Valida que el texto ingresado sea un número válido y positivo.
     * Útil para tarifas o cupos.
     */
    public static boolean esNumeroValido(String numero) {
        try {
            double valor = Double.parseDouble(numero);
            return valor >= 0;
        } catch (NumberFormatException e) {
            // Si contiene letras o símbolos raros, el parseo falla y retorna falso
            return false;
        }
    }

    /**
     * Valida un RUT mediante el cálculo del dígito verificador (Módulo 11).
     * Soporta formatos con o sin puntos y guión.
     */
    public static boolean esRutValido(String rut) {
        boolean validacion = false;
        try {
            // Limpiamos el string: mayúsculas, sin puntos ni guiones
            rut = rut.toUpperCase().replace(".", "").replace("-", "");

            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }
        } catch (Exception e) {
            // Si el RUT tiene letras donde van números, cae aquí
            return false;
        }
        return validacion;
    }
    /**
     * Valida que el teléfono contenga solo números y tenga una longitud válida.
     */
    public static boolean esTelefonoValido(String telefono) {
        // Expresión regular: Solo números, entre 8 y 11 caracteres de largo
        String regex = "^[0-9]{8,11}$";
        return Pattern.matches(regex, telefono);
    }
}

