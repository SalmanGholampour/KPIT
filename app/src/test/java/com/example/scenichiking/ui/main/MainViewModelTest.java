package com.example.scenichiking.ui.main;

import com.example.scenichiking.data.MarkerPoint;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {
    @Mock
    MainNavigator mainNavigator;
    private MainViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new MainViewModel();
        viewModel.setNavigator(mainNavigator);
    }


    @After
    public void tearDown() throws Exception {
        mainNavigator = null;
        viewModel = null;
    }

    @Test
    public void getRouteTest() throws Exception {
       viewModel.onGetRouteClick();
       verify(mainNavigator,times(1)).onGetRouteClick();
    }
    @Test
    public void overViewTest() throws Exception {
        viewModel.onOverViewClick();
        verify(mainNavigator,times(1)).onOverViewClick();
    }

    @Test
    public void setDataTest() throws Exception {
        List<MarkerPoint>markerPoints=new ArrayList<>();
        MarkerPoint point=new MarkerPoint();
        point.setFavorite(true);
        point.setPoint(new LatLng(50.928170, 10.486371));
        markerPoints.add(point);
        ///
        point=new MarkerPoint();
        point.setPoint(new LatLng(52.487639, 13.568117));
        point.setFavorite(false);
        markerPoints.add(point);
        viewModel.setMarkerPoints(markerPoints);
        Assert.assertEquals(viewModel.getMarkerPoints().size(),markerPoints.size());
        Assert.assertEquals(viewModel.getMarkerPoints().get(1),markerPoints.get(1));
        Assert.assertEquals(viewModel.getMarkerPoints().get(1).isFavorite(),false);
    }


}
