package com.jt17.mapsapp.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.jt17.mapsapp.R
import com.jt17.mapsapp.databinding.FragmentMapsBinding
import java.util.*

class MapsFragment : Fragment(R.layout.fragment_maps) {
    private var _binding: FragmentMapsBinding? = null
    private lateinit var binding: FragmentMapsBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 1000
    }

    private val callback = OnMapReadyCallback { googleMap ->

        val myHome = LatLng(41.475980, 69.582925)
        googleMap.addMarker(MarkerOptions().position(myHome).title("My Home"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myHome))
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.maps_retro_mode
            )
        )
        zoomCamera(googleMap, myHome)

        binding.getLocateButton.setOnClickListener {

            binding.LLLatitude.isVisible = false
            binding.LLLongitude.isVisible = false

            val mylocate = LatLng(
                googleMap.cameraPosition.target.latitude, googleMap.cameraPosition.target.longitude
            )

            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            binding.setLocationTxt.text = getAddress(mylocate, geocoder)
        }


        binding.getMyLocation.setOnClickListener {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())

            fetchLocation()
//            placeMarkerOn(googleMap, mylocate)

            binding.LLLatitude.isVisible = true
            binding.LLLongitude.isVisible = true
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)
        _binding = binding
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val intent = Intent()

    }


    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_LOCATION
            )
            return
        }


        task.addOnSuccessListener {
            if (it != null) {
                binding.latitude.text = it.latitude.toString()
                binding.longitude.text = it.longitude.toString()

                val mylocate = LatLng(it.latitude, it.longitude)
                val geocoder = Geocoder(requireContext(), Locale.getDefault())

                binding.setLocationTxt.text = getAddress(mylocate, geocoder)

            }
        }

    }

    private fun getAddress(latLng: LatLng, geocoder: Geocoder): String {
        val address: Address?
        var fullAddress = ""
        val addresses: List<Address>? =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (addresses!!.isNotEmpty()) {
            address = addresses[0]
            fullAddress = address.getAddressLine(0)

            var city = address.locality
            var state = address.adminArea
            var country = address.countryName
            var postalCode = address.postalCode
            var knownName = address.featureName

        } else {
            fullAddress = "Location not found"
        }

        return fullAddress
    }

    private fun zoomCamera(nMap: GoogleMap, location: LatLng) {
        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        nMap.animateCamera(CameraUpdateFactory.zoomIn())

        nMap.animateCamera(CameraUpdateFactory.zoomTo(10f), 3500, null)

        val cameraPosition =
            CameraPosition.builder().target(location).zoom(16f).bearing(90f).tilt(30f).build()
        nMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun placeMarkerOn(nMap: GoogleMap, currentLatLng: LatLng) {

        val markerOptions = MarkerOptions().position(currentLatLng)
        markerOptions.title("$currentLatLng")
        nMap.addMarker(markerOptions)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}