package com.laszlo.meditaken

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://ylypyvyscinqhtzxvivh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlseXB5dnlzY2lucWh0enh2aXZoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njc1MzI3MjksImV4cCI6MjA4MzEwODcyOX0.TvAL1Fh8J0KZ5wqSG96tkfjzilCIxlNvMsBL2he2x0Q"
    ) {
        install(Auth)
    }
}
