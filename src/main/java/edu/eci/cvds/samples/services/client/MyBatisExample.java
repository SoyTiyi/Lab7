/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;

/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del archivo
     * de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * 
     * @param args
     * @throws SQLException
     * @throws ParseException
     */
    public static void main(String args[]) throws SQLException, ParseException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        ItemMapper im = sqlss.getMapper(ItemMapper.class);
        System.out.println("---------------------------------------------");
        System.out.println("Consulta Clientes PA");
        System.out.println(cm.consultarClientes());
        System.out.println("---------------------------------------------");
        System.out.println("Consulta Items PA");
        System.out.println(im.consultarItems());
        System.out.println("---------------------------------------------");
        System.out.println("Consultar Cliente con id 147852");
        System.out.println(cm.consultarCliente(147852));
        System.out.println("---------------------------------------------");
        System.out.println("Consultar Item con id 2");
        System.out.println(im.consultarItem(1234));
        System.out.println("---------------------------------------------");
        /* Insertar Item Rentado */
        cm.agregarItemRentadoACliente(4, 2, new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-10"), new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-10"));
        /* Insertar Item */
        Item item = new Item(new TipoItem(2,"Pelis"),2159518,"prueba","descripcion prueba",new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-17"),(long)9000,"prueba","prueba");
        im.insertarItem(item);
        
        sqlss.commit();
        sqlss.close();

        
        
    }


}
