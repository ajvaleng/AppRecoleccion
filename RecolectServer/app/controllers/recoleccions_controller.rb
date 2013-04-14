class RecoleccionsController < ApplicationController
  # GET /recoleccions
  # GET /recoleccions.json
  def index
    @recoleccions = Recoleccion.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @recoleccions }
    end
  end

  # GET /recoleccions/1
  # GET /recoleccions/1.json
  def show
    @recoleccion = Recoleccion.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @recoleccion }
    end
  end

  # GET /recoleccions/new
  # GET /recoleccions/new.json
  def new
    @recoleccion = Recoleccion.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @recoleccion }
    end
  end

  # GET /recoleccions/1/edit
  def edit
    @recoleccion = Recoleccion.find(params[:id])
  end

  # POST /recoleccions
  # POST /recoleccions.json
  def create
    @recoleccion = Recoleccion.new(params[:recoleccion])

    respond_to do |format|
      if @recoleccion.save
        format.html { redirect_to @recoleccion, notice: 'Recoleccion was successfully created.' }
        format.json { render json: @recoleccion, status: :created, location: @recoleccion }
      else
        format.html { render action: "new" }
        format.json { render json: @recoleccion.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /recoleccions/1
  # PUT /recoleccions/1.json
  def update
    @recoleccion = Recoleccion.find(params[:id])

    respond_to do |format|
      if @recoleccion.update_attributes(params[:recoleccion])
        format.html { redirect_to @recoleccion, notice: 'Recoleccion was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @recoleccion.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /recoleccions/1
  # DELETE /recoleccions/1.json
  def destroy
    @recoleccion = Recoleccion.find(params[:id])
    @recoleccion.destroy

    respond_to do |format|
      format.html { redirect_to recoleccions_url }
      format.json { head :no_content }
    end
  end
end
