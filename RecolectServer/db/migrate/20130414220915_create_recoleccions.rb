class CreateRecoleccions < ActiveRecord::Migration
  def change
    create_table :recoleccions do |t|
      t.string :llegada_paradero
      t.string :salida_paradero
      t.int :presonas_suben
      t.int :personas_bajan
      t.double :long
      t.double :lat
      t.string :recorrido
      t.string :patente
      t.string :nombre
      t.int :puerta
      t.int :periodo

      t.timestamps
    end
  end
end
