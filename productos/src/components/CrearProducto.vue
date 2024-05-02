<script>
export default {
  data() {
    return {
      editando: true,
      tiposDeMongo: ['-- Selecciona un tipo --','String', 'Number', 'Date', 'Array', 'Object', 'Boolean'] // Tipos de datos en MongoDB
    };
  },
  methods:{
    limpiarTodo(){

    },
    nuevoAtributo(){
      let tiposDeMongo = ['-- Selecciona un tipo --','String', 'Number', 'Date', 'Array', 'Object', 'Boolean']
      let formulario = document.getElementById("cuerpo-form")
      let div = document.createElement("div")
      div.id="nuevo_atributo"

      let label = document.createElement("label")
      label.id="nuevo_atributo"
      label.innerText = "Nuevo Atributo:"

      let input_nombre = document.createElement("input")
      input_nombre.type = "text"

      let input_value = document.createElement("input")
      input_value.type = "text"

      let select = document.createElement("select")
      select.classList = "form-select"

      for(let tipo in tiposDeMongo){
        let opcion = document.createElement("option")
        opcion.innerText=tipo
        opcion.value=tipo
        if (tipo === '-- Selecciona un tipo --')
          opcion.selected

        select.appendChild(opcion)
      }
      div.append(label,input_nombre,input_value,select)
      formulario.append(div)
      input_nombre.addEventListener("blur",function (){

      })
    }
  }

};
</script>

<template>
<div>
  <h1>Nuevo Producto<span v-if="editando">*</span></h1>
  <form method="post" action="http://172.19.0.1:8080/producto/crear" enctype="multipart/form-data">
    <div class="modal-body" id="cuerpo-form">
      <div id="nombre">
        <label for="atr-nombre">Nombre:</label>
          <input type="text" hidden="hidden" name="_nombre" value="nombre">
          <input type="text" name="nombre" id="atr-nombre">
          <select class="form-select" name="tipo-nombre">
            <option value="">-- Selecciona un tipo --</option>
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" disabled :selected="'String'===tipo">{{ tipo }}</option>
          </select>
      </div>
      <div id="fecha_creacion">
        <label for="atr-fecha_creacion">Fecha de creación:</label>
          <input type="text" hidden="hidden" name="_fecha_creacion" value="fecha_creacion">
          <input type="date" name="fecha_creacion" id="atr-fecha_creacion">
          <select class="form-select" name="tipo-fecha_creacion">
            <option value="">-- Selecciona un tipo --</option>
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Date'===tipo">{{ tipo }}</option>
          </select>
      </div>
    </div>
    <div id="fecha_ultima_modificacion">
      <label for="atr-fecha_ultima_modificacion">Fecha de última modificación:</label>
        <input type="text" hidden="hidden" name="_fecha_ultima_modificacion" value="fecha_ultima_modificacion">
        <input type="date" name="fecha_ultima_modificacion" id="atr-fecha_ultima_modificacion">
        <select class="form-select" name="tipo-fecha_ultima_modificacion">
          <option value="">-- Selecciona un tipo --</option>
          <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Date'===tipo">{{ tipo }}</option>
        </select>
    </div>
    <div id="precio">
      <label for="atr-precio">Fecha de última modificación:</label>
      <input type="text" hidden="hidden" name="_precio" value="precio">
      <input type="number" name="precio" id="atr-precio">
      <select class="form-select" name="tipo-precio">
        <option value="">-- Selecciona un tipo --</option>
        <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Number'===tipo">{{ tipo }}</option>
      </select>
    </div>
    <div id="descripcion">
      <label for="atr-descripcion">Descripción:</label>
      <input type="text" hidden="hidden" name="_descripcion" value="descripcion">
      <textarea name="descripcion" id="atr-descripcion"></textarea>
      <select class="form-select" name="tipo-descripcion">
        <option value="">-- Selecciona un tipo --</option>
        <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo">{{ tipo }}</option>
      </select>
    </div>
    <div id="categoria">
      <label for="atr-categoria">Categoría:</label>
      <input type="text" hidden="hidden" name="_categoria" value="categoria">
      <input type="text" name="categoria" id="atr-categoria">
      <select class="form-select" name="tipo-categoria">
        <option value="">-- Selecciona un tipo --</option>
        <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo">{{ tipo }}</option>
      </select>
    </div>
    <div>
      <label for="atr-en_almacen">Unidades en almacén:</label>
      <input type="text" hidden="hidden" name="_en_almacen" value="en_almacen">
      <input type="number" name="en_almacen" id="atr-en_almacen">
      <select class="form-select" name="tipo-en_almacen">
        <option value="">-- Selecciona un tipo --</option>
        <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Number'===tipo">{{ tipo }}</option>
      </select>
    </div>
    <div>
      <label for="atr-tipo">Tipo:</label>
      <input type="text" hidden="hidden" name="_tipo" value="tipo">
      <input type="text" name="tipo" id="atr-tipo">
      <select class="form-select" name="tipo-tipo">
        <option value="">-- Selecciona un tipo --</option>
        <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo">{{ tipo }}</option>
      </select>
    </div>
    <div>
      <label for="atr-imagenes">Imagenes:</label>
      <input type="text" hidden="hidden" name="_imagenes" value="imagenes">
      <input type="file" name="imagenes" id="atr-imagenes" multiple>
      <select class="form-select" name="tipo-imagenes">
        <option value="Binary">Imagenes</option>
      </select>
    </div>
    <div id="botones-form" class="modal-footer">
      <input type="submit" value="Guardar producto" onclick="this.editando = false">
      <input type="reset" value="Borrar todo">
      <button onclick="limpiarTodo()">Limpiar todo</button>
      <button onclick="nuevoAtributo()"></button>
    </div>
  </form>
</div>
</template>

<style scoped>

</style>