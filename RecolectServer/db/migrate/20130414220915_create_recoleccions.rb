class CreateRecoleccions < ActiveRecord::Migration
  def change
    create_table :recoleccions do |t|
      t.string :llegada_paradero
      t.string :salida_paradero
      t.integer :presonas_suben
      t.integer :personas_bajan
      t.float :long
      t.float :lat
      t.string :recorrido
      t.string :patente
      t.string :nombre
      t.integer :puerta
      t.integer :periodo

      t.timestamps
    end
  end
end
