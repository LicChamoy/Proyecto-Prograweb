function validar() {
    var usuario, password, expresion3;

    
    usuario = document.getElementById("nombre_usuario").value;
    password = document.getElementById("pass").value;
  
    
 
    expresion3 = /^[a-zA-Z0-9\_\-]/;

    if (usuario === "" || password === "") {
        alert("Favor de ingresar todos los datos");
        return false;
    }else if(!expresion3.test(usuario)){
        alert("El usuario contiene caracteres no validos");
        return false;
    }else if(password.length<8 || !/[a-z]/.test(password) || !/[A-Z]/.test(password) || !/[0-9]/.test(password) || !/[.:;(){}-]/.test(password)){
        alert("La contraseña debe tener al menos 8 caracteres, una letra mayuscula, una letra minuscula, un digito y un signo de puntuacion");
        return false;
    }
    
  
}
