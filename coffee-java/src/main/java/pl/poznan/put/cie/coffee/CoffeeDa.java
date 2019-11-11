package pl.poznan.put.cie.coffee;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CoffeeDa {

    private final NamedParameterJdbcTemplate jdbc;

    public CoffeeDa(){
        this.jdbc = new NamedParameterJdbcTemplate( DbUtil.getDataSource(
                "jdbc:sqlite:C:/Users/Szymon/Desktop/Semestr5/PP/coffee-java/src/main/java/pl/poznan/put/cie/coffee/cf"));
    }

    /**
     * Returns a coffee with given name.
     *
     * @param name coffee name
     * @return coffee object or null
     */
    public Coffee get(final String name) {
        String sql = "SELECT sup_id, price, sales, total FROM coffees "
                + "WHERE cof_name = :cof_name";
        MapSqlParameterSource params = new MapSqlParameterSource("cof_name", name);
        return jdbc.query(sql, params, new ResultSetExtractor<Coffee>() {

            @Override
            public Coffee extractData(ResultSet rs) throws SQLException, DataAccessException {
                Coffee c = new Coffee();
                c.setName(name);
                c.setSupplierId(rs.getString("sup_id"));
                c.setPrice(rs.getString("price"));
                c.setSales(rs.getInt("sales"));
                c.setTotal(rs.getInt("total"));
                return c;
            }
        });
    }

    /**
     * Returns a list of all coffees.
     *
     * @return list of all coffees
     */
    public List<Coffee> getAll() {
        String sql = "SELECT cof_name, sup_id, price, sales, total FROM coffees";
        try {
            return jdbc.query(sql, new RowMapper<Coffee>() {
                        @Override
                        public Coffee mapRow(ResultSet rs, int rownumber) throws SQLException {
                            Coffee c = new Coffee();
                            c.setName(rs.getString(1));
                            c.setSupplierId(rs.getString("sup_id"));
                            c.setPrice(rs.getString("price"));
                            c.setSales(rs.getInt("sales"));
                            c.setTotal(rs.getInt("total"));
                            return c;
                        }
                    }
                    );
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    public void update(Coffee c) {
        String sql = "UPDATE coffees "
                + "SET price = :price, sales = :sales, total = :total "
                + "WHERE cof_name = :cof_name AND sup_id = :sup_id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("price", c.getPrice());
        parameters.put("sales", c.getSales());
        parameters.put("total", c.getTotal());
        parameters.put("cof_name", c.getName());
        parameters.put("sup_id", c.getSupplierId());
        jdbc.update(sql, parameters);
    }

    public void delete(String name, int supplierId) {
        String sql = "DELETE FROM coffees WHERE";
        sql = String.format("%s cof_name = \"%s\" AND sup_id = \"%s\" ", sql, name, supplierId);

        MapSqlParameterSource params = new MapSqlParameterSource("cof_name", name);
        params.addValue("sup_id", supplierId);

        jdbc.update(sql, params);
    }

    public void create(Coffee c) {
         String sql = "INSERT INTO coffees(cof_name, sup_id, price, sales, total) VALUES(";
         sql = String.format("%s\"%s\",", sql, c.getName());
         sql = sql + c.getSupplierId() + ",";
         sql = sql + c.getPrice() + ",";
         sql = sql + c.getSales() + ",";
         sql = sql + c.getTotal() + ")";


        MapSqlParameterSource params = new MapSqlParameterSource("cof_name", c.getName());

        System.out.println(sql);

        jdbc.update(sql, params);
    }

}
