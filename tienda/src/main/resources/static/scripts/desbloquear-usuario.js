function confirmarDesbloqueoUsuario(usuario) {
    if (confirm("¿Seguro que quieres desbloquear el usuario?")) {
        console.log("si");
        let id = usuario.id;
        console.log(id);
        $.get(`/user/desbloqueo/${id}`, function(data){
            if (data === '') {
                //
            } else {
                window.location.reload();
                window.location.href = window.location.href;
            }
        });

    } else {
        console.log("no")
    }
    window.location.reload();
    window.location.href = window.location.href;
}