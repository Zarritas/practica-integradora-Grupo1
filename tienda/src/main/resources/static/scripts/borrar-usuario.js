function confirmarBorradoUsuario(usuario) {
    if (confirm("¿Seguro que quieres borrar el usuario?")) {
        console.log("si")
        let id = usuario.id;
        window.location.assign(`http://localhost:8080/admin/borrado/${id}`);
    } else {
        console.log("no")
    }
}