function validar() {
    var nombres, apellidos, correo, password, Cpassword, expresion, expresion2, expresion3, fechaNac;

    nombres = document.getElementById("nombre").value;
    apellidos = document.getElementById("apellido").value;
    correo = document.getElementById("correo").value;
    password = document.getElementById("password").value;
    Cpassword = document.getElementById("Cpassword").value;


    expresion = /\w+@\w+\.+[a-z]/;
    expresion2 = /^[a-zA-ZÀ-ÿ\s]/;
    expresion3 = /^[a-zA-Z0-9\_\-]/;
    

    if (nombres === "" || apellidos === "" || correo === "" || usuario === "" || password === "" || Cpassword === "") {
        alert("Favor de ingresar todos los datos");
        return false;
    }else if(!expresion2.test(nombres)){
        alert("El nombre tiene caracteres invalidos, solo ingresar letras");
        return false;
    }else if(!expresion2.test(apellidos)){
        alert("Los apellidos tiene caracteres invalidos, solo ingresar letras");
        return false;
    }else if(usuario.length>20){
        alert("Nombre de usuario demasiado largo");
        return false;
    }else if(!expresion3.test(usuario)){
        alert("El usuario contiene caracteres no validos");
        return false;
    }else if(password.length<8 || !/[a-z]/.test(password) || !/[A-Z]/.test(password) || !/[0-9]/.test(password) || !/[.:;(){}-]/.test(password)){
        alert("La contraseña debe tener al menos 8 caracteres, una letra mayuscula, una letra minuscula, un digito y un signo de puntuacion");
        return false;
    }else if(password !== Cpassword){
        alert("Las contraseñas no coinciden");
        return false;
    }

}

function validate(){
    var email = document.getElementById("correo").value;

    var regx = /^([a-zA-Z0-9\._]+)@([a-zA-Z0-9])+.([a-z])(.[a-z]+)?$/

    if (regx.test(email)){
        return true
    }else{
         alert("Favor de proporcionar un correo valido.")
        return false;
    }            

}

function validarFecha(){
    var f = new Date();
    var mes = (f.getMonth() + 1).toString();
    var dia = f.getDate().toString();
    fechaNac = document.getElementById("fecha").value;

    //alert("prueba")

    if(mes.length <= 1){

    mes = "0" + mes;
    }

    if(dia.length <= 1){
         dia= "0" + dia;
     }
    fechaActual = f.getFullYear() + "-" + mes + "-" + dia;

    if(fechaNac > fechaActual){
        alert("La fecha seleccionada no se puede ingresar")
        return false
    }else{
        return true
    }
}
