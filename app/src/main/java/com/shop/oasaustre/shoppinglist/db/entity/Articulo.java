package com.shop.oasaustre.shoppinglist.db.entity;


import com.shop.oasaustre.shoppinglist.db.dao.ArticuloDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;

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
public class Articulo {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;

    @Property
    @NotNull
    private String nombre;

    @Property(nameInDb = "CODIGO_BARRAS")
    private String codigoBarras;

    @ToMany(referencedJoinProperty = "idArticulo")
    private List<ListaCompra> listaCompra;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1788488102)
    private transient ArticuloDao myDao;



    @Generated(hash = 1908453761)
    public Articulo() {
    }

    @Generated(hash = 1813387959)
    public Articulo(Long id, @NotNull String nombre, String codigoBarras) {
        this.id = id;
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
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


    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 291069498)
    public List<ListaCompra> getListaCompra() {
        if (listaCompra == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListaCompraDao targetDao = daoSession.getListaCompraDao();
            List<ListaCompra> listaCompraNew = targetDao
                    ._queryArticulo_ListaCompra(id);
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
    @Generated(hash = 990936458)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getArticuloDao() : null;
    }

    @Override
    public String toString(){
        return nombre;
    }
}


