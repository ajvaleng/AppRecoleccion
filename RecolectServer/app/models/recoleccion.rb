class Recoleccion < ActiveRecord::Base
  attr_accessible :lat, :llegada_paradero, :long, :nombre, :patente, :periodo, :personas_bajan, :presonas_suben, :puerta, :recorrido, :salida_paradero
end
