<template>
  <VRow>
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Ajouter une Catégorie </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addCat"
              @submit.prevent="addCat"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="10"
                >
                  <VTextField
                    v-model="categorie.nom"
                    placeholder="ex : ......"
                    label="Catégorie"
                    density="compact"
                    :rules="[rules.required]"
                    clearable
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="2"
                >
                  <VBtn
                    type="submit"
                    block
                  >
                    Enregistrer
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste des Catégories </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VTextField
              v-model="search"
              placeholder="Recherchez quelque chose ......"
              label="Search"
              density="compact"
              clearable
              persistent-placeholder
            />
            <VDataTable
              :headers="headers"
              :items="categorieList"
              :search="search"
              :items-per-page="5"
              density="compact"
            >
              <template #item.actions="{ item }">
                <VDialog
                  v-model="modifier"
                  persistent
                  class="v-dialog-sm"
                >
                  <!-- Dialog Activator -->
                  <template #activator="{ props }">
                    <VBtn
                      variant="text"
                      @click="open(item.raw)"
                      v-bind="props"
                    >
                      <VIcon icon="mdi-backup-outline"></VIcon>
                      <VTooltip
                        location="top"
                        activator="parent"
                      >
                        Modifier la catégorie {{ item.raw.nom }}
                      </VTooltip>
                    </VBtn>
                  </template>

                  <!-- Dialog Content -->
                  <VCard title="Modifier une Catégorie">
                    <VForm
                      ref="updateCat"
                      @submit.prevent="updateCat"
                      class="mt-1 mb-1"
                    >
                      <DialogCloseBtn
                        variant="text"
                        size="small"
                        @click="modifier = false"
                      />

                      <VCardText>
                        <VRow>
                          <VCol
                            cols="12"
                            md="12"
                          >
                            <VTextField
                              v-model="nom"
                              placeholder="ex : ......"
                              label="Catégorie"
                              density="compact"
                              :rules="[rules.required]"
                              clearable
                              persistent-placeholder
                            />
                          </VCol>
                        </VRow>
                      </VCardText>

                      <VCardActions>
                        <VSpacer />
                        <VBtn
                          color="error"
                          variant="outlined"
                          @click="modifier = false"
                        >
                          Annuler
                        </VBtn>
                        <VBtn
                          color="primary"
                          variant="outlined"
                          type="submit"
                        >
                          Modifier
                        </VBtn>
                      </VCardActions>
                    </VForm>
                  </VCard>
                </VDialog>
              </template>
            </VDataTable>
          </VExpansionPanelText>
        </VExpansionPanel>
      </VExpansionPanels>
    </VCol>
  </VRow>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { useToast } from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

export default {
  components: {
    VDataTable,
  },

  setup() {
    const search = ref('')
    const toast = useToast()
    const ajouter = ref(false)
    const modifier = ref(false)
    const headers = [
      { title: 'ID', sortable: false, key: 'id' },
      { title: 'NOMS', key: 'nom' },
      { title: 'ACTIONS', sortable: false, key: 'actions' },
    ]

    return {
      toast,
      search,
      ajouter,
      headers,
      modifier,
    }
  },

  data() {
    return {
      search: '',
      categorieList: [],
      id: '',
      nom: '',
      categorie: {
        nom: '',
        statut: '',
      },
      student: '',

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getDatas()

    let account = JSON.parse(localStorage.getItem('account'))
    console.log(account)
    if (!account) {
      Swal.fire({
        icon: 'warning',
        text: 'Attention, cette connexion est illégitime.',
        toast: true,
        position: 'top-end',
        showCancelButton: false,
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
      })
      this.$router.push('/login')
    } else {
      if (account.created_by != 'etudiant') {
        this.student = false
      }
    }
  },

  methods: {
    async getDatas() {
      try {
        axios
          .get('allCat')
          .then(response => {
            this.categorieList = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async addCat() {
      try {
        const valid = this.$refs.addCat.validate()
        console.log(valid)
        if (valid) {
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous enregistrer la catégorie <b>' + this.categorie.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addCat', {
                  nom: this.categorie.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.categorie.nom = ''
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },

    async open(data) {
      this.modifier = true
      this.id = data.id
      this.nom = data.nom
    },

    async updateCat() {
      try {
        const valid = this.$refs.updateCat.validate()
        console.log(valid)
        if (valid) {
          this.modifier = false
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous modifier cette catégorie  en : <b>' + this.categorie.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .put('updateCat/' + this.id, {
                  nom: this.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.modifier = false
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },
  },
}
</script>
