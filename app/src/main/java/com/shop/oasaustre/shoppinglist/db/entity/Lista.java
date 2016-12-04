package com.shop.oasaustre.shoppinglist.db.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;

/**
 * Created by AsaustreGarO on 29/11/2016.
 */

@Entity(createInDb = false)
public class Lista {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;

    @Property
    @NotNull
    private String nombre;

    @Property
    @NotNull
    private Long fecha;

    @Property
    @NotNull
    private Long activo;

    @ToMany(referencedJoinProperty = "idLista")
    private List<ListaCompra> listaCompra;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 252327487)
    private transient ListaDao myDao;


    @Generated(hash = 1767194985)
    public Lista(Long id, @NotNull String nombre, @NotNull Long fecha,
            @NotNull Long activo) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.activo = activo;
    }

    @Generated(hash = 1173415040)
    public Lista() {
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

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Long getActivo() {
        return activo;
    }

    public void setActivo(Long activo) {
        this.activo = activo;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1379752365)
    public List<ListaCompra> getListaCompra() {
        if (listaCompra == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListaCompraDao targetDao = daoSession.getListaCompraDao();
            List<ListaCompra> listaCompraNew = targetDao
                    ._queryLista_ListaCompra(id);
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
    @Generated(hash = 875573628)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getListaDao() : null;
    }
}
