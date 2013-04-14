require 'test_helper'

class RecoleccionsControllerTest < ActionController::TestCase
  setup do
    @recoleccion = recoleccions(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:recoleccions)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create recoleccion" do
    assert_difference('Recoleccion.count') do
      post :create, recoleccion: { lat: @recoleccion.lat, llegada_paradero: @recoleccion.llegada_paradero, long: @recoleccion.long, nombre: @recoleccion.nombre, patente: @recoleccion.patente, periodo: @recoleccion.periodo, personas_bajan: @recoleccion.personas_bajan, presonas_suben: @recoleccion.presonas_suben, puerta: @recoleccion.puerta, recorrido: @recoleccion.recorrido, salida_paradero: @recoleccion.salida_paradero }
    end

    assert_redirected_to recoleccion_path(assigns(:recoleccion))
  end

  test "should show recoleccion" do
    get :show, id: @recoleccion
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @recoleccion
    assert_response :success
  end

  test "should update recoleccion" do
    put :update, id: @recoleccion, recoleccion: { lat: @recoleccion.lat, llegada_paradero: @recoleccion.llegada_paradero, long: @recoleccion.long, nombre: @recoleccion.nombre, patente: @recoleccion.patente, periodo: @recoleccion.periodo, personas_bajan: @recoleccion.personas_bajan, presonas_suben: @recoleccion.presonas_suben, puerta: @recoleccion.puerta, recorrido: @recoleccion.recorrido, salida_paradero: @recoleccion.salida_paradero }
    assert_redirected_to recoleccion_path(assigns(:recoleccion))
  end

  test "should destroy recoleccion" do
    assert_difference('Recoleccion.count', -1) do
      delete :destroy, id: @recoleccion
    end

    assert_redirected_to recoleccions_path
  end
end
