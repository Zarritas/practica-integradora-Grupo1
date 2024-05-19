function confirmarBorradoUsuario(usuario) {
    if (confirm("¿Seguro que quieres borrar el usuario?")) {
        console.log("si")
        let id = usuario.id;
        $.get(`/user/borrado/${id}`, function(data){
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
