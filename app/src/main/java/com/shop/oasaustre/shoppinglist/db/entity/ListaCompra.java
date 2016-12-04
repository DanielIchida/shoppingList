package com.shop.oasaustre.shoppinglist.db.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.TiendaDao;
import com.shop.oasaustre.shoppinglist.db.dao.CategoriaDao;
import com.shop.oasaustre.shoppinglist.db.dao.ArticuloDao;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;

/**
 * Created by AsaustreGarO on 29/11/2016.
 */

@Entity(createInDb = false)
public class ListaCompra {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;

    @Property(nameInDb = "IDLISTA")
    private Long idLista;

    @Property(nameInDb = "IDARTICULO")
    private Long idArticulo;

    @Property(nameInDb = "IDCATEGORIA")
    private Long idCategoria;

    @Property(nameInDb = "IDTIENDA")
    private Long idTienda;

    @ToOne(joinProperty = "idLista")
    private Lista lista;




    @ToOne(joinProperty = "idArticulo")
    private Articulo articulo;

    @ToOne(joinProperty = "idCategoria")
    private Categoria categoria;

    @ToOne(joinProperty = "idTienda")
    private Tienda tienda;

    @Property
    private Long unidades;

    @Property
    private Double precio;

    @Property
    private String notas;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 682974165)
    private transient ListaCompraDao myDao;


    @Generated(hash = 1432032348)
    public ListaCompra(Long id, Long idLista, Long idArticulo, Long idCategoria,
            Long idTienda, Long unidades, Double precio, String notas) {
        this.id = id;
        this.idLista = idLista;
        this.idArticulo = idArticulo;
        this.idCategoria = idCategoria;
        this.idTienda = idTienda;
        this.unidades = unidades;
        this.precio = precio;
        this.notas = notas;
    }

    @Generated(hash = 1762769533)
    public ListaCompra() {
    }

    @Generated(hash = 718069819)
    private transient Long lista__resolvedKey;

    @Generated(hash = 344637210)
    private transient Long articulo__resolvedKey;

    @Generated(hash = 1426606615)
    private transient Long categoria__resolvedKey;

    @Generated(hash = 505866720)
    private transient Long tienda__resolvedKey;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Long idTienda) {
        this.idTienda = idTienda;
    }

    public Long getUnidades() {
        return unidades;
    }

    public void setUnidades(Long unidades) {
        this.unidades = unidades;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2005029956)
    public Lista getLista() {
        Long __key = this.idLista;
        if (lista__resolvedKey == null || !lista__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListaDao targetDao = daoSession.getListaDao();
            Lista listaNew = targetDao.load(__key);
            synchronized (this) {
                lista = listaNew;
                lista__resolvedKey = __key;
            }
        }
        return lista;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 921327973)
    public void setLista(Lista lista) {
        synchronized (this) {
            this.lista = lista;
            idLista = lista == null ? null : lista.getId();
            lista__resolvedKey = idLista;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2086729685)
    public Articulo getArticulo() {
        Long __key = this.idArticulo;
        if (articulo__resolvedKey == null || !articulo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ArticuloDao targetDao = daoSession.getArticuloDao();
            Articulo articuloNew = targetDao.load(__key);
            synchronized (this) {
                articulo = articuloNew;
                articulo__resolvedKey = __key;
            }
        }
        return articulo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1083339580)
    public void setArticulo(Articulo articulo) {
        synchronized (this) {
            this.articulo = articulo;
            idArticulo = articulo == null ? null : articulo.getId();
            articulo__resolvedKey = idArticulo;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2004511963)
    public Categoria getCategoria() {
        Long __key = this.idCategoria;
        if (categoria__resolvedKey == null
                || !categoria__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaDao targetDao = daoSession.getCategoriaDao();
            Categoria categoriaNew = targetDao.load(__key);
            synchronized (this) {
                categoria = categoriaNew;
                categoria__resolvedKey = __key;
            }
        }
        return categoria;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 842589333)
    public void setCategoria(Categoria categoria) {
        synchronized (this) {
            this.categoria = categoria;
            idCategoria = categoria == null ? null : categoria.getId();
            categoria__resolvedKey = idCategoria;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1051126656)
    public Tienda getTienda() {
        Long __key = this.idTienda;
        if (tienda__resolvedKey == null || !tienda__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TiendaDao targetDao = daoSession.getTiendaDao();
            Tienda tiendaNew = targetDao.load(__key);
            synchronized (this) {
                tienda = tiendaNew;
                tienda__resolvedKey = __key;
            }
        }
        return tienda;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1698789274)
    public void setTienda(Tienda tienda) {
        synchronized (this) {
            this.tienda = tienda;
            idTienda = tienda == null ? null : tienda.getId();
            tienda__resolvedKey = idTienda;
        }
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
    @Generated(hash = 1456840910)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getListaCompraDao() : null;
    }
}
