package com.shop.oasaustre.shoppinglist.db.entity;

import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.dao.TiendaDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by AsaustreGarO on 29/11/2016.
 */

@Entity(createInDb = false)
public class Tienda {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;

    @Property
    @NotNull
    private String nombre;

    @Property
    private String direccion;

    @Property
    private String poblacion;

    @Property
    private String provincia;

    @ToMany(referencedJoinProperty = "idTienda")
    private List<ListaCompra> listaCompra;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1199998804)
    private transient TiendaDao myDao;


    @Generated(hash = 1281166770)
    public Tienda(Long id, @NotNull String nombre, String direccion,
            String poblacion, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
    }

    @Generated(hash = 2120058016)
    public Tienda() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    @Override
    public String toString(){
        return nombre;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1080629239)
    public List<ListaCompra> getListaCompra() {
        if (listaCompra == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListaCompraDao targetDao = daoSession.getListaCompraDao();
            List<ListaCompra> listaCompraNew = targetDao
                    ._queryTienda_ListaCompra(id);
            synchronized (this) {
                if (listaCompra == null) {
                    listaCompra = listaCompraNew;
                }
            }
        }
        return listaCompra;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1913772122)
    public synchronized void resetListaCompra() {
        listaCompra = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1902720290)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTiendaDao() : null;
    }
}
