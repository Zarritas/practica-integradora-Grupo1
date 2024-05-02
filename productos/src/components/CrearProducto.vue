<script>

export default {
  data() {
    return {
      editando: true,
      tiposDeMongo: ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean'] // Tipos de datos en MongoDB
    };
  },
  methods:{
    limpiarTodo(){
      console.log("nada")
    },
    nuevoAtributo(){
  let tiposDeMongo = ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean']
  let formulario = document.getElementById("cuerpo-form")
  let div = document.createElement("div")
  div.id="nuevo_atributo"

  let label = document.createElement("label")
  label.id="nuevo_atributo"
  label.innerText = "Nuevo Atributo:"

  let input_nombre = document.createElement("input")
  input_nombre.type = "text"
  input_nombre.hidden = true

  let input_value = document.createElement("input")
  input_value.type = "text"

  let select = document.createElement("select")
  select.classList = 'form-select'

  for(let tipo of tiposDeMongo){
    let opcion = document.createElement("option")
    opcion.innerText=tipo
    opcion.value=tipo
    if (tipo === 'String')
      opcion.selected
    select.appendChild(opcion)
  }
  div.append(label,input_nombre,input_value,select)
  formulario.append(div)
  input_value.addEventListener("blur",function (){
    let nuevo_atributo = document.getElementById("nuevo_atributo")
    let name = nuevo_atributo.children[2].value
    nuevo_atributo.children[0].removeAttribute("id")
    nuevo_atributo.children[0].setAttribute("for",name)
    nuevo_atributo.children[0].innerText = name+': '
    nuevo_atributo.children[1].setAttribute("name","_"+name)
    nuevo_atributo.children[1].setAttribute("value",name)
    nuevo_atributo.children[2].setAttribute("name",name)
    nuevo_atributo.children[3].setAttribute("name","tipo-"+name)
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
      <div class="row">
        <div id="nombre">
          <div class="col-md-4">
            <label for="atr-nombre">Nombre:</label>
          </div>
          <div class="col-md-4">
            <input type="text" hidden="hidden" name="_nombre" value="nombre">
            <input type="text" name="nombre" id="atr-nombre">
          </div>
          <div class="col-md-4">
            <select class="form-select" name="tipo-nombre">
              <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" disabled :selected="'String'===tipo">{{ tipo }}</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div id="fecha_creacion">
          <div class="col-md-4">
            <label for="atr-fecha_creacion">Fecha de creación:</label>
          </div>
          <div class="col-md-4">
            <input type="text" hidden="hidden" name="_fecha_creacion" value="fecha_creacion">
            <input type="date" name="fecha_creacion" id="atr-fecha_creacion">
          </div>
          <div class="col-md-4">
            <select class="form-select" name="tipo-fecha_creacion">
              <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Date'===tipo" disabled>{{ tipo }}</option>
            </select>
          </div>
        </div>
      </div>
      <div id="fecha_ultima_modificacion">
        <div class="col-md-4">
          <label for="atr-fecha_ultima_modificacion">Fecha de última modificación:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_fecha_ultima_modificacion" value="fecha_ultima_modificacion">
          <input type="date" name="fecha_ultima_modificacion" id="atr-fecha_ultima_modificacion">
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-fecha_ultima_modificacion">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Date'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div id="precio">
        <div class="col-md-4">
          <label for="atr-precio">Fecha de última modificación:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_precio" value="precio">
          <input type="number" name="precio" id="atr-precio">
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-precio">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Number'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div id="descripcion">
        <div class="col-md-4">
          <label for="atr-descripcion">Descripción:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_descripcion" value="descripcion">
          <textarea name="descripcion" id="atr-descripcion"></textarea>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-descripcion">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div id="categoria">
        <div class="col-md-4">
          <label for="atr-categoria">Categoría:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_categoria" value="categoria">
          <input type="text" name="categoria" id="atr-categoria">
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-categoria">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div id="almacen">
        <div class="col-md-4">
          <label for="atr-en_almacen">Unidades en almacén:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_en_almacen" value="en_almacen">
          <input type="number" name="en_almacen" id="atr-en_almacen">
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-en_almacen">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'Number'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div>

        <div class="col-md-4">
          <label for="atr-tipo">Tipo:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_tipo" value="tipo">
          <input type="text" name="tipo" id="atr-tipo">
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-tipo">
            <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="tipo" :selected="'String'===tipo" disabled>{{ tipo }}</option>
          </select>
        </div>
      </div>
      <div>
        <div class="col-md-4">
          <label for="atr-imagenes">Imagenes:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_imagenes" value="imagenes">
          <input type="file" name="imagenes" id="atr-imagenes" multiple>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-imagenes">
            <option value="Binary" disabled>Imagenes</option>
          </select>
        </div>
      </div>
    </div>
    <div id="botones-form" class="modal-footer">
      <input type="submit" value="Guardar producto" onclick="this.editando = false" class="btn btn-success">
      <div @click="limpiarTodo()" class="btn btn-secondary">Limpiar todo</div>
      <div @click="nuevoAtributo()" class="btn btn-primary">Nuevo Atributo</div>
    </div>
  </form>
</div>
</template>

<style scoped>

</style>