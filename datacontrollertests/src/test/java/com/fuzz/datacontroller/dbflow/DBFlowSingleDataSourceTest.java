package com.fuzz.datacontroller.dbflow;

import com.fuzz.datacontroller.DataControllerResponse;
import com.fuzz.datacontroller.source.DataSource;
import com.fuzz.datacontroller.BaseRobolectricUnitTest;
import com.fuzz.datacontroller.DataItem;
import com.fuzz.datacontroller.ValidateCallback;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBFlowSingleDataSourceTest extends BaseRobolectricUnitTest {

    private DataSource<DataItem> dataSource;

    @Before
    public void setup_test() {
        dataSource = DBFlowSingleSource.builderInstance(DataItem.class).build();
    }

    @Test
    public void test_canGet() {
        DataItem model = new DataItem();
        model.setId("Andrew");
        model.setData("Test");
        dataSource.store(new DataControllerResponse<>(model, DataSource.SourceType.NETWORK));

        ValidateCallback<DataItem> callback = new ValidateCallback<>();
        dataSource.get(null, callback, callback);
        assertTrue(callback.isSuccessCalled());
        assertNull(callback.getError());
        DataControllerResponse<DataItem> response = callback.getResponse();
        assertNotNull(response);

        DataItem item = response.getResponse();
        assertNotNull(item);
        assertEquals("Andrew", item.getId());
    }

    @Test
    public void test_canStore() {
        DataItem model = new DataItem();
        model.setId("Andrew");

        dataSource.store(new DataControllerResponse<>(model, DataSource.SourceType.NETWORK));

        model = dataSource.getStoredData();
        assertNotNull(model);
        assertEquals("Andrew", model.getId());
    }
}